package com.example.workout_journal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_journal.Route
import com.example.workout_journal.data.entity.Workout
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.WorkoutWithHIIT
import com.example.workout_journal.data.relations.WorkoutWithRunning
import com.example.workout_journal.data.relations.WorkoutWithWeightExercises
import com.example.workout_journal.data.repository.WorkoutRepository
import com.example.workout_journal.ui.viewmodel.WorkoutsWithType.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

sealed class WorkoutsWithType(val timeStamp: Long, val workoutId: Long) {

    data class Weight(val data: WorkoutWithWeightExercises) :
        WorkoutsWithType(data.workout.dateCreated, data.workout.id)

    data class Run(val data: WorkoutWithRunning) :
        WorkoutsWithType(data.workout.dateCreated, data.workout.id)

    data class HIIT(val data: WorkoutWithHIIT) :
        WorkoutsWithType(data.workout.dateCreated, data.workout.id)
}

sealed class WorkoutDetailResult {
    data class Weight(val workout: WorkoutWithWeightExercises) : WorkoutDetailResult()
    data class Run(val workout: WorkoutWithRunning) : WorkoutDetailResult()
    data class HIIT(val workout: WorkoutWithHIIT) : WorkoutDetailResult()
}
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    val allWorkouts: StateFlow<List<WorkoutsWithType>> = combine(
        workoutRepository.weightWorkouts(),
        workoutRepository.runWorkouts(),
        workoutRepository.hiitWorkouts()
    ) { weight, run, hiit ->
        val weightItem = weight.map { Weight(it) }
        val runItem = run.map { Run(it) }
        val hiitItem = hiit.map { HIIT(it) }
        (weightItem + runItem + hiitItem).sortedBy { it.timeStamp }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    private val _selection = MutableStateFlow<Pair<Long,WorkoutType>?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedWorkout: StateFlow<WorkoutDetailResult?> = _selection.filterNotNull()
        .flatMapLatest { (id,type) ->
        when (type) {
                WorkoutType.WEIGHTS -> workoutRepository.getWeightWorkout(id)
                    .map { WorkoutDetailResult.Weight(it) }

                WorkoutType.RUN -> workoutRepository.getRunWorkout(id)
                    .map { WorkoutDetailResult.Run(it) }

                WorkoutType.HIIT -> workoutRepository.getHIITWorkout(id)
                    .map { WorkoutDetailResult.HIIT(it) }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    fun selectWorkout(workoutId: Long, workoutType: WorkoutType) {
        _selection.value = Pair(workoutId, workoutType)
    }


    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.deleteWorkout(workout)
        }
    }
}


