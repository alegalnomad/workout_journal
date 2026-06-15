package com.example.workout_journal.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.ui.content.SettingsScreenContent
import com.example.workout_journal.ui.viewmodel.SettingsUiState
import com.example.workout_journal.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onSelectExercise: () -> Unit
)
{
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedExercises by viewModel.selectedExerciseNameIds.collectAsStateWithLifecycle()
    val allExercises by viewModel.allExercises.collectAsStateWithLifecycle()

    SettingsScreenContent(
        uiState = uiState,
        allExercises = allExercises,
        selectedExercises = selectedExercises,
        onMeasureUnitChange = viewModel::updateMeasureUnit,
        onUserNameChange = viewModel::updateUserName,

    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenContentPreview() {
    SettingsScreenContent(
        uiState = previewUiState,
        allExercises = previewExercises,
        onMeasureUnitChange = {},
        onUserNameChange = {},

    )
}


// Preview data
private val previewUiState = SettingsUiState(
    measureUnit = MeasureUnit.METRIC,
    userName = "Raghav",
    selectedExerciseIds = setOf(1, 3, 5)
)

private val previewExercises = listOf(
    WeightExerciseName(id = 1, name = "Bench Press"),
    WeightExerciseName(id = 2, name = "Squat"),
    WeightExerciseName(id = 3, name = "Deadlift"),
    WeightExerciseName(id = 4, name = "Overhead Press"),
    WeightExerciseName(id = 5, name = "Barbell Row"),
    WeightExerciseName(id = 6, name = "Pull Up"),
)