package com.example.workout_journal.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_journal.data.datastore.UserPreferencesManager
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.Run
import com.example.workout_journal.data.entity.RunSplits
import com.example.workout_journal.data.entity.Workout
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.repository.RunRepository
import com.example.workout_journal.data.repository.WorkoutRepository
import com.example.workout_journal.service.RunTrackingService
import com.example.workout_journal.utils.toKm
import com.example.workout_journal.utils.toMiles
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val runRepository: RunRepository,
    private val workoutRepository: WorkoutRepository,
    private val preferencesManager: UserPreferencesManager
) : ViewModel() {

    val measureUnit: StateFlow<MeasureUnit> = preferencesManager.userPreferencesFlow
        .map { it.measureUnit }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MeasureUnit.METRIC
        )

    private val currentDistance = RunTrackingService.currentDistance

    val uiDistance = combine(currentDistance, measureUnit) { distance, unit ->
        if (unit == MeasureUnit.IMPERIAL) toMiles(distance) else toKm(distance)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    val activeTime = RunTrackingService.activeTimeMillis
    val totalTime = RunTrackingService.totalTimeMillis
    val activeSplits = RunTrackingService.activeSplits
    val totalElevationGain = RunTrackingService.totalElevationGain
    val totalElevationLoss = RunTrackingService.totalElevationLoss
    val pathPoints = RunTrackingService.pathPoints
    val title = MutableStateFlow<String>("")

    fun startRun() {
        val intent = Intent(context, RunTrackingService::class.java).apply {
            action = "START"
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun pauseResume() {
        val intent = Intent(context, RunTrackingService::class.java).apply {
            action = "PAUSE_RESUME"
        }
        context.startService(intent)
    }

    fun stopRun() {
        val intent = Intent(context, RunTrackingService::class.java).apply {
            action = "STOP"
        }
        context.startService(intent)
    }

    fun saveRun(title: String?, note: String?) {
        viewModelScope.launch {
            try {
                val workoutId =
                    workoutRepository.insertWorkout(Workout(workoutType = WorkoutType.RUN))
                val run = Run(
                    workoutId = workoutId,
                    title = title,
                    distanceMeters = currentDistance.value,
                    timeElapsed = totalTime.value,
                    activeTime = activeTime.value,
                    elevationGain = totalElevationGain.value,
                    elevationalLoss = totalElevationLoss.value,
                    notes = note,
                    polyPath = pathPoints.value
                )
                val runId = runRepository.insertRun(run)
                val runSplits = activeSplits.value.map { split ->
                    RunSplits(
                        runId = runId,
                        splitIndex = split.splitIndex,
                        splitTime = split.splitTime,
                        elevationGain = split.elevationGain,
                        elevationalLoss = split.elevationalLoss
                    )
                }
                runRepository.saveRunSplits(runSplits)
                runRepository.evaluateAndStorePBs(run,runId,runSplits)

            }catch (e: Exception){
                Log.e("RunViewModel", "Save failed", e)
            }

        }
    }

}