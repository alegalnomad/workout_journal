package com.example.workout_journal.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ServiceInfo
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.workout_journal.R
import com.example.workout_journal.data.datastore.UserPreferencesManager
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.utils.distanceLabel
import com.example.workout_journal.utils.formatElapsedTime
import com.example.workout_journal.utils.toKm
import com.example.workout_journal.utils.toMiles
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs


data class TempSplit(
    val splitIndex: Int,
    val splitTime: Long,
    val elevationGain: Double,
    val elevationalLoss: Double,
)

class RunTrackingService : LifecycleService() {

    @Inject
    lateinit var preferencesManager: UserPreferencesManager

    private val contentPendingIntent: PendingIntent by lazy {
        val openAppIntent = packageManager.getLaunchIntentForPackage(packageName)
            ?.apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP }
        PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var totalDistanceMetres = 0.0
    private var lastSplitMetres = 0.0
    private val splitThreshold = 1000.0 // 1km in metres
    private val movementSpeedThreshold = 0.5 // m/s (approx 1.8 km/h) to count as "active"
    private var lastSplitTime = 0L

    // Elevation Tracking
    private var lastAltitude: Double? = null
    private var lastLocationTime: Long? = null
    private var currentSplitGain = 0.0
    private var currentSplitLoss = 0.0
    private var startTime = 0L
    private var timeAtPause = 0L
    private var timerJob: Job? = null


    companion object {

        val trackingError = MutableStateFlow<String?>(null)
        val pathPoints = MutableStateFlow<List<LatLng>>(emptyList())
        val currentDistance = MutableStateFlow(0.0)
        val totalElevationGain = MutableStateFlow(0.0)
        val totalElevationLoss = MutableStateFlow(0.0)
        val activeTimeMillis = MutableStateFlow(0L)
        val totalTimeMillis = MutableStateFlow<Long>(0L)
        val activeSplits = MutableStateFlow<List<TempSplit>>(emptyList())
        val isAutoPaused = MutableStateFlow(false)
        val isManuallyPaused = MutableStateFlow(false)

    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START" -> {
                startTime = System.currentTimeMillis()
                lastSplitTime = startTime
                isManuallyPaused.value = false
                startForegroundService()
                startTimer()
            }

            "PAUSE_RESUME" -> pauseResumeService()
            "STOP" -> stopService()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun pauseResumeService() {
        if (isManuallyPaused.value) {
            // RESUMING
            val pauseDuration = System.currentTimeMillis() - timeAtPause
            startTime += pauseDuration // Shift start time forward by the pause duration
            lastSplitTime += pauseDuration
            lastLocationTime = System.currentTimeMillis()

            isManuallyPaused.value = false
            startTimer()
            // Re-enable GPS
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            }
        } else {
            // PAUSING
            isManuallyPaused.value = true
            timeAtPause = System.currentTimeMillis()
            timerJob?.cancel()
            fusedLocationClient.removeLocationUpdates(locationCallback)
            updateNotification(currentDistance.value, totalTimeMillis.value, true)
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = lifecycleScope.launch {
            while (true) {
                val elapsed = System.currentTimeMillis() - startTime
                totalTimeMillis.value = elapsed

                updateNotification(
                    currentDistance.value, elapsed, isManuallyPaused.value || isAutoPaused.value
                )

                delay(1000) // Update every second
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun startLocationUpdates() {

        val fineLoc =
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (!fineLoc) {
            trackingError.value = "High accuracy location permission is required to track runs."
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            return
        }

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setMinUpdateIntervalMillis(2000L).build()

        try {
            fusedLocationClient.requestLocationUpdates(
                request, locationCallback, Looper.getMainLooper()
            )
        } catch (unlikely: SecurityException) {
            unlikely.printStackTrace()

            // 2. Stop the service immediately to avoid a crash
            // (Foreground services without permissions are killed by Android)
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()

            // 3. Update a StateFlow so the UI can show an error message
            trackingError.value = "Location permissions were revoked. Cannot track run."
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            if (isManuallyPaused.value) return
            val location = result.lastLocation ?: return
            val currentTime = System.currentTimeMillis()

            //Check for Auto-Pause
            val isMoving = location.speed >= movementSpeedThreshold && location.accuracy < 20
            isAutoPaused.value = !isMoving

            // Track Active Time (Speed check)
            lastLocationTime?.let { lastTime ->
                if (isMoving) {
                    val timeDiff = currentTime - lastTime
                    activeTimeMillis.update { it + timeDiff }
                }
            }
            lastLocationTime = currentTime

            if (isMoving) {

                // Update Map Path
                val newPoint = LatLng(location.latitude, location.longitude)
                pathPoints.value += newPoint


                //Track Elevation Change
                if (location.hasAltitude()) {
                    lastAltitude?.let { lastAlt ->
                        val diff = location.altitude - lastAlt
                        if (diff > 0) {
                            totalElevationGain.update { it + diff }
                            currentSplitGain += diff
                        } else {
                            totalElevationLoss.update { it + abs(diff) }
                            currentSplitLoss += abs(diff)
                        }
                    }
                    lastAltitude = location.altitude
                }

                //Split Logic
                if (pathPoints.value.size > 1) {
                    val lastPoint = pathPoints.value[pathPoints.value.size - 2]
                    val distanceResult = FloatArray(1)
                    Location.distanceBetween(
                        lastPoint.latitude,
                        lastPoint.longitude,
                        newPoint.latitude,
                        newPoint.longitude,
                        distanceResult
                    )
                    totalDistanceMetres += distanceResult[0]
                    currentDistance.value = totalDistanceMetres

                    updateNotification(
                        totalDistanceMetres, System.currentTimeMillis() - startTime, false
                    )

                    checkAndSaveSplit()
                }

            }
            if (location.hasAltitude()) {
                lastAltitude = location.altitude
            }
        }
    }

    private fun checkAndSaveSplit() {
        if (totalDistanceMetres >= lastSplitMetres + splitThreshold) {
            saveSplitToDb(1.0)
            lastSplitMetres += splitThreshold
            lastSplitTime = System.currentTimeMillis()
        }
    }

    private fun stopService() {
        timerJob?.cancel()

        val finalDistance = (totalDistanceMetres - lastSplitMetres) / 1000.0
        if (finalDistance > 0) {
            saveSplitToDb(finalDistance)
        }
        val currentTime = System.currentTimeMillis()

        totalTimeMillis.value = currentTime - startTime

        fusedLocationClient.removeLocationUpdates(locationCallback)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun saveSplitToDb(distance: Double) {
        val currentTime = System.currentTimeMillis()
        val duration = currentTime - lastSplitTime

        val newSplit = TempSplit(
            splitIndex = activeSplits.value.size + 1,
            splitTime = duration,
            elevationGain = currentSplitGain,
            elevationalLoss = currentSplitLoss
        )

        activeSplits.update { it + newSplit }

        lastSplitTime = currentTime
        currentSplitGain = 0.0
        currentSplitLoss = 0.0

    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun startForegroundService() {
        createNotificationChannel()
        val notification =
            NotificationCompat.Builder(this, "run_channel").setContentTitle("Workout Journal")
                .setContentText("Tracking your run...")
                .setSmallIcon(R.drawable.ic_launcher_foreground).setOngoing(true).build()

        // minSdk is 33, so foregroundServiceType is required for location
        startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
        startLocationUpdates()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "run_channel", "Run Tracking", NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun updateNotification(distance: Double, time: Long, isPaused: Boolean) {

        lifecycleScope.launch {
            val unit = preferencesManager.userPreferencesFlow.map { it.measureUnit }.first()
            val statsText = formatNotificationText(distance, time, unit)

            val notification =
                NotificationCompat.Builder(this@RunTrackingService, "run_channel")
                    .setContentTitle(setStatusText(isPaused))
                    .setContentText(statsText)
                    .setContentIntent(contentPendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setOngoing(true)
                    .setSilent(true)
                    .setOnlyAlertOnce(true)
                    .build()

            val manager = getSystemService(NotificationManager::class.java)
            manager.notify(1, notification)
        }
    }

    private fun setStatusText(isPaused: Boolean): String =
        if (isPaused) "Paused" else "Tracking Run"

    private fun formatNotificationText(
        distanceMetres: Double,
        timeMillis: Long,
        unit: MeasureUnit
    ): String {
        val distance = if (unit == MeasureUnit.METRIC) {
            toKm(distanceMetres)
        } else {
            toMiles(distanceMetres)
        }
        val timeStr = formatElapsedTime(timeMillis)

        return "Distance: %.2f ${unit.distanceLabel()}  |  Time: %s".format(distance, timeStr)
    }

}




