package com.example.workout_journal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_journal.data.datastore.UserPreferences
import com.example.workout_journal.data.datastore.UserPreferencesManager
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.data.repository.WeightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SettingsUiState(
    val measureUnit: MeasureUnit = MeasureUnit.METRIC,
    val userName: String = "User",
    val selectedExerciseIds: Set<Int> = emptySet()
)


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: UserPreferencesManager,
    private val weightRepository: WeightRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> =
        preferencesManager.userPreferencesFlow.map { preferences ->
            SettingsUiState(
                measureUnit = preferences.measureUnit,
                userName = preferences.userName,
                selectedExerciseIds = preferences.selectedExerciseIds
            )
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState()
        )

    val allExercises : StateFlow<List<WeightExerciseName>> = weightRepository.allNames
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedExerciseNameIds = MutableStateFlow(emptySet<Int>())
    val selectedExerciseNameIds: StateFlow<Set<Int>> = _selectedExerciseNameIds.asStateFlow()

    init {
        viewModelScope.launch {
            val saved = preferencesManager.userPreferencesFlow.map { it.selectedExerciseIds }.first()
            _selectedExerciseNameIds.value = saved
        }
    }

    fun updateMeasureUnit(unit: MeasureUnit) {
        viewModelScope.launch {
            preferencesManager.updateMeasureUnit(unit)
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            preferencesManager.updateUserName(name)
        }
    }

    fun saveSelectedExerciseIds(ids: Set<Int>) {
        viewModelScope.launch {
            preferencesManager.saveSelectedExerciseIds(ids)
        }
    }

    fun toggleExerciseId(id: Int) {
        viewModelScope.launch {
            val current =
                preferencesManager.userPreferencesFlow.map { it.selectedExerciseIds }.first()
            val updated = if (id in current) {
                current - id
            } else {
                current + id
            }
            preferencesManager.saveSelectedExerciseIds(updated)
        }
    }
}


