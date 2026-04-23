package com.example.workout_journal.ui.cards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.WorkoutWithRunning

@Composable
fun RunWorkoutCard(
    workout: WorkoutWithRunning,
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
){
    Card(
        modifier = Modifier.padding(),
        onClick = { onWorkoutSelect(workout.workout.id, workout.workout.workoutType) }
    ) {

    }

}