package com.example.workout_journal.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.workout_journal.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class TimerState(
    val phase: String = "delay",
    val secondsLeft: Int = 0,
    val currentRound: Int = 0,
    val totalRounds: Int = 0,
    val currentSet: Int = 0,
    val totalSets: Int = 0,
    val currentExercise: String = "",
    val isRunning: Boolean = false,
    val isDone: Boolean = false
)


class HIITService : LifecycleService() {
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

    // ... rest of the class
    private var timerJob: Job? = null

    companion object {
        private val _timerState = MutableStateFlow(TimerState())
        val isPaused = MutableStateFlow(false)
        val timerState: StateFlow<TimerState> = _timerState.asStateFlow()
    }


    override fun onCreate() {
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START" -> {
                val sets = intent.getIntExtra("sets", 0)
                val rounds = intent.getIntExtra("rounds", 0)
                val workSeconds = intent.getIntExtra("workSeconds", 0)
                val restSeconds = intent.getIntExtra("restSeconds", 0)
                val setRestSeconds = intent.getIntExtra("setRestSeconds", 0)
                val delayStart = intent.getIntExtra("delayStart", 0)
                val myExerciseList = intent.getStringArrayListExtra("exerciseList")

                isPaused.value = false

                startForegroundService()
                startTimer(
                    sets,
                    rounds,
                    workSeconds,
                    restSeconds,
                    setRestSeconds,
                    delayStart,
                    myExerciseList
                )
            }

            "PAUSE_RESUME" -> {
                pauseResumeService()
            }

            "STOP" -> {
                stopService()
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun startTimer(
        sets: Int,
        rounds: Int,
        workSeconds: Int,
        restSeconds: Int,
        setRestSeconds: Int,
        delayStart: Int,
        myExerciseList: ArrayList<String>?
    ) {
        timerJob?.cancel()
        timerJob = lifecycleScope.launch {

            // --- Delay phase ---
            if (delayStart > 0) {
                _timerState.update {
                    it.copy(
                        phase = "delay",
                        currentExercise = "Get Ready",
                        isRunning = true,
                        totalSets = sets,
                        totalRounds = rounds
                    )
                }
                updateNotification(_timerState.value)
                updateTimerState(delayStart)
            } else {
                _timerState.update {
                    it.copy(
                        isRunning = true, totalSets = sets, totalRounds = rounds
                    )
                }
                updateNotification(_timerState.value)
            }

            // --- Main loop ---
            for (set in 1..sets) {
                for (round in 1..rounds) {
                    // Work phase
                    _timerState.update {
                        it.copy(
                            phase = "work",
                            currentSet = set,
                            currentRound = round,
                            currentExercise = myExerciseList?.getOrNull(round - 1) ?: ""
                        )
                    }
                    updateTimerState(workSeconds)

                    // Rest between rounds (skip after last round in a set)
                    if (round < rounds) {
                        _timerState.update { it.copy(phase = "rest", currentExercise = "Rest") }
                        updateTimerState(restSeconds)
                    }
                }

                // Set rest (skip after last set)
                if (set < sets) {
                    _timerState.update { it.copy(phase = "setRest", currentExercise = "Set Rest") }
                    updateTimerState(setRestSeconds)
                }
            }

            // --- Done ---
            _timerState.update { it.copy(isDone = true, isRunning = false) }
            updateNotification(_timerState.value)
        }
    }

    private fun pauseResumeService() {
        if (!isPaused.value) {
            isPaused.value = true
            _timerState.update { it.copy(isRunning = false) }
        } else {
            val state = _timerState.value
            if (state.isDone || state.isRunning) return
            _timerState.update { it.copy(isRunning = true) }
            isPaused.value = false
        }
        updateNotification(_timerState.value)

    }

    private suspend fun updateTimerState(secondsLeft: Int) {
        for (seconds in secondsLeft downTo 0) {
            while (isPaused.value) {
                delay(200)
            }
            _timerState.update { it.copy(secondsLeft = seconds) }
            updateNotification(_timerState.value)
            delay(1000)
        }
    }

    private fun stopService() {
        timerJob?.cancel()
        isPaused.value = false
        _timerState.update { TimerState() }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        timerJob?.cancel()
        super.onDestroy()
    }

    private fun startForegroundService() {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, "hiit_timer_channel")
            .setContentTitle("Workout Journal").setContentText("Starting Workout...")
            .setSmallIcon(R.drawable.ic_launcher_foreground).setOngoing(true).build()

        // minSdk is 33, so foregroundServiceType is required for location
        startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "hiit_timer_channel", "HIIT Timer", NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }


    private fun buildNotification(state: TimerState): NotificationCompat.Builder {
        // Pause / Resume action
        val pauseResumeIntent = Intent(this, HIITService::class.java).apply {
            action = "PAUSE_RESUME"
        }
        val pauseResumePending = PendingIntent.getService(
            this,
            1,
            pauseResumeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Stop action
        val stopIntent = Intent(this, HIITService::class.java).apply { action = "STOP" }
        val stopPending = PendingIntent.getService(
            this, 2, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = buildNotificationTitle(state)
        val body = buildNotificationBody(state)
        val pauseLabel = if (isPaused.value) "Resume" else "Pause"

        return NotificationCompat.Builder(this, "hiit_timer_channel")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle(title)
            .setContentText(body).setContentIntent(contentPendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .setOnlyAlertOnce(true).addAction(0, pauseLabel, pauseResumePending)
            .addAction(0, "Stop", stopPending)
    }

    private fun buildNotificationTitle(state: TimerState): String = when (state.phase) {
        "delay" -> "Get Ready!"
        "work" -> "Work — ${state.currentExercise}"
        "rest" -> "Rest"
        "setRest" -> "Set Rest"
        else -> if (state.isDone) "Workout Complete 🎉" else "HIIT Timer"
    }

    private fun buildNotificationBody(state: TimerState): String {
        if (state.isDone) return "Great job! Tap to view your session."
        val setInfo = "Set ${state.currentSet}/${state.totalSets}"
        val roundInfo = "Round ${state.currentRound}/${state.totalRounds}"
        val timeLeft = "${state.secondsLeft}s left"
        return when (state.phase) {
            "delay" -> "${state.secondsLeft}s until start"
            "work", "rest" -> "$setInfo · $roundInfo · $timeLeft"
            "setRest" -> "$setInfo · $timeLeft"
            else -> ""
        }
    }

    private fun updateNotification(state: TimerState) {
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(1, buildNotification(state).build())
    }
}


