package com.example.workout_journal.ui.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.ui.cards.HIITWorkoutCard
import com.example.workout_journal.ui.cards.RunWorkoutCard
import com.example.workout_journal.ui.cards.WeightWorkoutCard
import com.example.workout_journal.ui.viewmodel.WorkoutsWithType

@Composable
fun HomeScreenContent(
    workouts: List<WorkoutsWithType>,
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
    measureUnit: MeasureUnit
) {
    LazyColumn() {
        items(items = workouts, key = {it.workoutId}) {
            WorkoutCard(
                workout = it,
                onWorkoutSelect = onWorkoutSelect,
                measureUnit
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun WorkoutCard(
    workout: WorkoutsWithType,
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
    measureUnit: MeasureUnit,
) {
    when (workout) {
        is WorkoutsWithType.Weight -> {
            WeightWorkoutCard(
                workout = workout.data,
                onWorkoutSelect = onWorkoutSelect,
                measureUnit
            )
        }
        is WorkoutsWithType.Run -> {
            RunWorkoutCard(
                workout = workout.data,
                onWorkoutSelect = onWorkoutSelect,
                measureUnit
            )
        }
        is WorkoutsWithType.HIIT -> {
            HIITWorkoutCard(
                workout = workout.data,
                onWorkoutSelect = onWorkoutSelect,

            )
        }
    }
}