package com.example.workout_journal.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.ui.cards.HIITWorkoutCard
import com.example.workout_journal.ui.cards.RunWorkoutCard
import com.example.workout_journal.ui.cards.WeightWorkoutCard
import com.example.workout_journal.ui.viewmodel.WorkoutsWithType

@Composable
fun HomeScreenContent(
    workouts: List<WorkoutsWithType>,
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
) {
    LazyColumn() {
        items(items = workouts, key = {it.workoutId}) {
            WorkoutCard(
                workout = it,
                onWorkoutSelect = onWorkoutSelect
            )
        }
    }
}

@Composable
fun WorkoutCard(
    workout: WorkoutsWithType,
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
) {
    when (workout) {
        is WorkoutsWithType.Weight -> {
            WeightWorkoutCard(
                workout = workout.data,
                onWorkoutSelect = onWorkoutSelect
            )
        }
        is WorkoutsWithType.Run -> {
            RunWorkoutCard(
                workout = workout.data,
                onWorkoutSelect = onWorkoutSelect
            )
        }
        is WorkoutsWithType.HIIT -> {
            HIITWorkoutCard(
                workout = workout.data,
                onWorkoutSelect = onWorkoutSelect
            )
        }
    }
}