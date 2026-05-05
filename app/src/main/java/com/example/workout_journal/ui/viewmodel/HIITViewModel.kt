package com.example.workout_journal.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workout_journal.Route
import com.example.workout_journal.data.entity.HIITExercise
import com.example.workout_journal.data.entity.HIITExerciseName
import com.example.workout_journal.data.entity.HIITSession
import com.example.workout_journal.data.entity.Workout
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.repository.HIITRepository
import com.example.workout_journal.data.repository.WorkoutRepository
import com.example.workout_journal.service.HIITService
import com.example.workout_journal.service.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HIITState(
    val sets: Int = 0,
    val rounds: Int = 0,
    val workSeconds: Int = 0,
    val restSeconds: Int = 0,
    val setRestSeconds: Int = 0,
    val delayTimer: Int = 0,
    val exerciseList: List<String> = emptyList()
)

@HiltViewModel
class HIITViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val hiitRepository: HIITRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HIITState())
    val uiState = _uiState.asStateFlow()

    var selectedExercises: List<HIITExerciseName> = emptyList()

    // Observe timer state from hiit service
    val timerState: StateFlow<TimerState> = HIITService.timerState

    fun updateSets(sets: Int) {
        _uiState.update { it.copy(sets = sets) }
    }

    fun updateRounds(rounds: Int) {
        _uiState.update { it.copy(rounds = rounds) }
    }

    fun updateWorkSeconds(seconds: Int) {
        _uiState.update { it.copy(workSeconds = seconds) }
    }

    fun updateRestSeconds(seconds: Int) {
        _uiState.update { it.copy(restSeconds = seconds) }
    }

    fun updateSetRestSeconds(seconds: Int) {
        _uiState.update { it.copy(setRestSeconds = seconds) }
    }

    fun updateDelayTimer(seconds: Int) {
        _uiState.update { it.copy(delayTimer = seconds) }
    }

    fun updateExerciseList(exerciseList: List<HIITExerciseName>) {

        selectedExercises += exerciseList
        _uiState.update { it.copy(exerciseList = exerciseList.map { exerciseName -> exerciseName.name }) }
    }

    // Get all exercise names for dropdown
    private val _searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val dropDownExercises = _searchQuery.debounce(300).flatMapLatest { query ->
        hiitRepository.searchNames(query)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()

    )

    fun searchQuery(query: String) {
        _searchQuery.value = query
    }

    // Start service and exercise
    fun startWorkout() {
        val state = _uiState.value
        val intent = Intent(context, HIITService::class.java).apply {
            action = "START"
            putExtra("sets", state.sets)
            putExtra("rounds", state.rounds)
            putExtra("workSeconds", state.workSeconds)
            putExtra("restSeconds", state.restSeconds)
            putExtra("setRestSeconds", state.setRestSeconds)
            putExtra("delayStart", state.delayTimer)
            putStringArrayListExtra("exerciseList", ArrayList(state.exerciseList))
        }
        ContextCompat.startForegroundService(context, intent)
    }

    //Pause and Stop
    fun pauseWorkout() {
        val intent = Intent(context, HIITService::class.java).apply {
            action = "PAUSE_RESUME"
        }
        context.startService(intent)
    }

    fun stopWorkout() {
        val intent = Intent(context, HIITService::class.java).apply {
            action = "STOP"
        }
        context.startService(intent)
    }

    // Save workout from summary screen
    fun saveWorkout(note: String?) {
        try {
            viewModelScope.launch {
                val state = _uiState.value
                val workoutId =
                    workoutRepository.insertWorkout(Workout(workoutType = WorkoutType.HIIT))
                val hiitSessionId = hiitRepository.addHIITSession(
                    HIITSession(
                        workoutId = workoutId,
                        rounds = state.rounds,
                        sets = state.sets,
                        roundDuration = state.workSeconds,
                        restDuration = state.restSeconds,
                        setRest = state.setRestSeconds,
                        notes = note
                    )
                )
                selectedExercises.forEachIndexed { index, exerciseName ->
                    hiitRepository.addExercise(
                        HIITExercise(
                            sessionId = hiitSessionId,
                            exerciseNameId = exerciseName.id,
                            order = index + 1
                        )
                    )
                }
            }
        }catch (e: Exception){
            Log.e("HIITViewModel", "Save failed", e)
        }
    }


}