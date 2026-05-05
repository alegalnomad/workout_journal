package com.example.workout_journal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.workout_journal.data.datastore.UserPreferencesManager
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.SetType
import com.example.workout_journal.data.entity.WeightExercise
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.data.entity.WeightSet
import com.example.workout_journal.data.entity.Workout
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.repository.WeightRepository
import com.example.workout_journal.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class Exercise(
    val id: Int,
    val exerciseName: String,
    val exerciseSets: List<Sets>,
    val notes: String,
)

data class Sets(
    val weight: Double,
    val reps: Int,
    val set : Int,
    val setType: SetType,

)

@HiltViewModel
class WeightsViewModel @Inject constructor(
    private val weightsRepository: WeightRepository,
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
    private val _uiState = MutableStateFlow<List<Exercise>>(emptyList())
    val uiState = _uiState.asStateFlow()

    private val _searchResults = MutableStateFlow<List<WeightExerciseName>>(emptyList())
    val searchResult: StateFlow<List<WeightExerciseName>> = _searchResults.asStateFlow()

    fun searchExercise(query:String){
        viewModelScope.launch {
            _searchResults.value = weightsRepository.searchName(query)
        }
    }


    fun addSelectedExerciseName(id: Int) {
        viewModelScope.launch {
            val exercise = weightsRepository.selectedExercise(id)
            val newExercise = Exercise(
                id = exercise!!.id,
                exerciseName = exercise.name,
                exerciseSets = emptyList(),
                notes = "",
            )
            _uiState.update { currentList ->
                currentList + newExercise
            }
        }
    }

    fun addSets(weight: Double, reps: Int, setType: String, exerciseID: Int) {
        _uiState.update { currentList ->
            currentList.map { exercise ->
                if (exercise.id == exerciseID) {
                    exercise.copy(exerciseSets = exercise.exerciseSets + Sets(
                        weight = weight,
                        reps = reps,
                        setType = SetType.valueOf(setType),
                        set = exercise.exerciseSets.size+1
                    ))
                } else {
                    exercise
                }
            }
        }
    }

    fun addNote(note: String, exerciseID: Int) {
        _uiState.update { currentList ->
            currentList.map { exercise ->
                if (exercise.id == exerciseID) {
                    exercise.copy(notes = note)
                } else {
                    exercise
                }
            }
        }
    }



    fun onSave() {
        viewModelScope.launch {
            val currentUnit = measureUnit.value
            try {
                val workoutID =
                    workoutRepository.insertWorkout(Workout(workoutType = WorkoutType.WEIGHTS))

                _uiState.value.forEach { exercise ->
                    val weightExercise = WeightExercise(
                        workoutId = workoutID,
                        exerciseNameId = exercise.id,
                        notes = exercise.notes
                    )
                    val weightExerciseID = weightsRepository.addExercise(weightExercise)

                    val weightSets = exercise.exerciseSets.map { set ->
                        WeightSet(
                            weightExerciseId = weightExerciseID,
                            set = set.set,
                            reps = set.reps,
                            weightKg = if (currentUnit == MeasureUnit.IMPERIAL) set.weight * 0.453592 else set.weight,
                            setType = set.setType
                        )

                    }
                    weightsRepository.addSets(weightSets)
                }
                weightsRepository.refreshPrsForWorkout(workoutID)
            }catch (e: Exception){
                Log.e("WeightViewModel", "Save failed", e)
            }
        }
    }


}


