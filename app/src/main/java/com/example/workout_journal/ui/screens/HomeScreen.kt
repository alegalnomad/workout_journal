package com.example.workout_journal.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.workout_journal.data.entity.HIITExercise
import com.example.workout_journal.data.entity.HIITExerciseName
import com.example.workout_journal.data.entity.HIITSession
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.Run
import com.example.workout_journal.data.entity.SetType
import com.example.workout_journal.data.entity.WeightExercise
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.data.entity.WeightSet
import com.example.workout_journal.data.entity.Workout
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.HIITRoundsExerciseWithName
import com.example.workout_journal.data.relations.HIITRoundsWithExercises
import com.example.workout_journal.data.relations.WeightExerciseWithSets
import com.example.workout_journal.data.relations.WorkoutWithHIIT
import com.example.workout_journal.data.relations.WorkoutWithRunning
import com.example.workout_journal.data.relations.WorkoutWithWeightExercises
import com.example.workout_journal.ui.buttons.SpeedDialFAB
import com.example.workout_journal.ui.content.HomeScreenContent
import com.example.workout_journal.ui.theme.Workout_journalTheme
import com.example.workout_journal.ui.viewmodel.HomeViewModel
import com.example.workout_journal.ui.viewmodel.WorkoutsWithType


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
    onExerciseSelection: (String) ->Unit
) {
    val workouts by viewModel.allWorkouts.collectAsStateWithLifecycle()
    val measureUnit by viewModel.measureUnit.collectAsStateWithLifecycle()

    SpeedDialFAB(onExerciseSelection)

    HomeScreenContent(
        workouts = workouts,
        onWorkoutSelect = onWorkoutSelect,
        measureUnit = measureUnit
    )
}
// Refactored to use a stateless content composable to support Previews



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Workout_journalTheme {
        // Use the stateless content composable with mock data for the Preview
        HomeScreenContent(
            workouts = fakeWorkouts,
            onWorkoutSelect = { _, _ -> },
            measureUnit = MeasureUnit.IMPERIAL
        )
    }
}

val fakeWorkouts: List<WorkoutsWithType> = listOf(

    // --- Weight ---
    WorkoutsWithType.Weight(
        data = WorkoutWithWeightExercises(
            workout = Workout(
                id = 1L,
                workoutType = WorkoutType.WEIGHTS,
                dateCreated = 1_700_000_000_000L
            ),
            exercises = listOf(
                WeightExerciseWithSets(
                    exercise = WeightExercise(
                        id = 1L,
                        workoutId = 1L,
                        exerciseNameId = 1,
                        notes = "Felt strong"
                    ),
                    exerciseName = WeightExerciseName(id = 1, name = "Bench Press"),
                    sets = listOf(
                        WeightSet(
                            id = 1L,
                            weightExerciseId = 1L,
                            set = 1,
                            reps = 8,
                            weightKg = 80.0,
                            setType = SetType.WORKING
                        ),
                        WeightSet(
                            id = 2L,
                            weightExerciseId = 1L,
                            set = 2,
                            reps = 8,
                            weightKg = 82.5,
                            setType = SetType.WORKING
                        ),
                        WeightSet(
                            id = 3L,
                            weightExerciseId = 1L,
                            set = 3,
                            reps = 6,
                            weightKg = 85.0,
                            setType = SetType.FAILURE
                        ),
                    )
                ),
                WeightExerciseWithSets(
                    exercise = WeightExercise(
                        id = 2L,
                        workoutId = 1L,
                        exerciseNameId = 2,
                        notes = null
                    ),
                    exerciseName = WeightExerciseName(id = 2, name = "Overhead Press"),
                    sets = listOf(
                        WeightSet(
                            id = 4L,
                            weightExerciseId = 2L,
                            set = 1,
                            reps = 10,
                            weightKg = 50.0,
                            setType = SetType.WARMUP
                        ),
                        WeightSet(
                            id = 5L,
                            weightExerciseId = 2L,
                            set = 2,
                            reps = 8,
                            weightKg = 60.0,
                            setType = SetType.DROP
                        ),
                    )
                )
            )
        )
    ),

    // --- Run ---
    WorkoutsWithType.Run(
        data = WorkoutWithRunning(
            workout = Workout(
                id = 2L,
                workoutType = WorkoutType.RUN,
                dateCreated = 1_700_100_000_000L
            ),
            runs =
                Run(
                    id = 1L,
                    workoutId = 2L,
                    title = "Morning 5K",
                    distanceMeters = 5.0,
                    timeElapsed = 1800_000L,
                    activeTime = 1750_000L,
                    elevationGain = 45.0,
                    elevationalLoss = 40.0,
                    notes = "Good pace",
                    polyPath = emptyList()

                )
        )
    ),

    // --- HIIT ---
    WorkoutsWithType.HIIT(
        data = WorkoutWithHIIT(
            workout = Workout(
                id = 3L,
                workoutType = WorkoutType.HIIT,
                dateCreated = 1_700_200_000_000L
            ),
            config = HIITRoundsWithExercises(
                rounds = HIITSession(
                    id = 1L,
                    workoutId = 3L,
                    rounds = 4,
                    sets = 3,
                    roundDuration = 40,
                    restDuration = 20,
                    setRest = 60,
                    notes = ""
                ),
                exercises = listOf(
                    HIITRoundsExerciseWithName(
                        exercise = HIITExercise(
                            id = 1L,
                            sessionId = 1L,
                            exerciseNameId = 1,
                            order = 1
                        ),
                        exerciseName = HIITExerciseName(id = 1, name = "Burpees")
                    ),
                    HIITRoundsExerciseWithName(
                        exercise = HIITExercise(
                            id = 2L,
                            sessionId = 1L,
                            exerciseNameId = 2,
                            order = 2
                        ),
                        exerciseName = HIITExerciseName(id = 2, name = "Mountain Climbers")
                    )
                )
            )
        )
    )
)