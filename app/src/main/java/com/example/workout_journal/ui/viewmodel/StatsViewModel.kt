package com.example.workout_journal.ui.viewmodel

import androidx.compose.remote.creation.first
import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.compose.ui.text.style.TextDecoration.Companion.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_journal.data.datastore.UserPreferencesManager
import com.example.workout_journal.data.entity.RunPB
import com.example.workout_journal.data.entity.WeightPB
import com.example.workout_journal.data.relations.WeightPbWithNames
import com.example.workout_journal.data.repository.RunRepository
import com.example.workout_journal.data.repository.WeightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class StatsUiState(
    val weightPBs: List<WeightPbWithNames> = emptyList(),
    val runPBs: List<RunPB> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val weightRepository: WeightRepository,
    private val runRepository: RunRepository,
    private val preferencesManager: UserPreferencesManager
) : ViewModel() {
    private val _selectedExerciseNameIds = MutableStateFlow(emptySet<Int>())
    val selectedExerciseNameIds: StateFlow<Set<Int>> = _selectedExerciseNameIds.asStateFlow()

    init {
        viewModelScope.launch {
            val saved = preferencesManager.userPreferencesFlow.map { it.selectedExerciseIds }.first()
            _selectedExerciseNameIds.value = saved
        }
    }

    fun clearSelection() {
        _selectedExerciseNameIds.value = emptySet()
    }

    fun saveSelectedExerciseIds(ids: Set<Int>) {
        viewModelScope.launch {
            preferencesManager.saveSelectedExerciseIds(ids)
        }
    }
    fun toggleExerciseId(id: Int) {
        viewModelScope.launch {
            val current = preferencesManager.userPreferencesFlow.map {it.selectedExerciseIds}.first()
            val updated = if (id in current) {
                current - id
            } else {
                current + id
            }
            preferencesManager.saveSelectedExerciseIds(updated)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val stats: StateFlow<StatsUiState> = combine(
        _selectedExerciseNameIds.flatMapLatest { ids ->
            if (ids.isEmpty()) weightRepository.allCurrentPbs()
            else weightRepository.allCurrentPbs().map { pbs ->
                pbs.filter { it.pb.exerciseNameId in ids }
            } // or full history
        },
        runRepository.getAllRunPB()
    ) { weightPBs, runPBs ->
        StatsUiState(
            weightPBs = weightPBs,
            runPBs = runPBs,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsUiState()
    )
}
