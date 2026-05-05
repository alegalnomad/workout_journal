package com.example.workout_journal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.RunDistance
import com.example.workout_journal.data.entity.RunPB
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.data.entity.WeightPB
import com.example.workout_journal.data.relations.WeightPbWithNames
import com.example.workout_journal.ui.content.StatsScreenContent
import com.example.workout_journal.ui.theme.Workout_journalTheme
import com.example.workout_journal.ui.viewmodel.StatsUiState
import com.example.workout_journal.ui.viewmodel.StatsViewModel

@Composable
fun StatsScreen(viewModel: StatsViewModel = hiltViewModel())
{
    val selectedExercises by viewModel.selectedExerciseNameIds.collectAsStateWithLifecycle()
    val stats by viewModel.stats.collectAsStateWithLifecycle()

    StatsScreenContent(stats = stats)

}



@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
        StatsScreenContent(
            stats = dummyStatsUiState
        )
    }

val dummyStatsUiState = StatsUiState(
    isLoading = false,
    weightPBs = listOf(
        WeightPbWithNames(
            pb = WeightPB(
                id = 1,
                exerciseNameId = 1,
                weightExerciseId = 101L,
                weightKg = 100.0,
                dateAchieved = System.currentTimeMillis()
            ),
            exerciseName = WeightExerciseName(id = 1, name = "Bench Press")
        ),
        WeightPbWithNames(
            pb = WeightPB(
                id = 2,
                exerciseNameId = 2,
                weightExerciseId = 102L,
                weightKg = 140.0,
                dateAchieved = System.currentTimeMillis()
            ),
            exerciseName = WeightExerciseName(id = 2, name = "Deadlift")
        ),
        WeightPbWithNames(
            pb = WeightPB(
                id = 3,
                exerciseNameId = 3,
                weightExerciseId = 103L,
                weightKg = 120.0,
                dateAchieved = System.currentTimeMillis()
            ),
            exerciseName = WeightExerciseName(id = 3, name = "Squat")
        )
    ),
    runPBs = listOf(
        RunPB(
            id = 1,
            runId = 201L,
            workoutId = 301L,
            distance = RunDistance.FIVE_KM,
            timeElapsed = 1320000L,   // 22 mins in ms
            totalDistanceMeters = 5000.0
        ),
        RunPB(
            id = 2,
            runId = 202L,
            workoutId = 302L,
            distance = RunDistance.TEN_KM,
            timeElapsed = 2940000L,   // 49 mins in ms
            totalDistanceMeters = 10000.0
        )
    )
)