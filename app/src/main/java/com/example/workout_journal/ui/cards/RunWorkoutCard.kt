package com.example.workout_journal.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.Run
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.WorkoutWithRunning
import com.example.workout_journal.utils.distanceLabel
import com.example.workout_journal.utils.toDate

@Composable
fun RunWorkoutCard(
    workout: WorkoutWithRunning,
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
    unit: MeasureUnit,
) {

    Card(
        modifier = Modifier.padding(),
        onClick = { onWorkoutSelect(workout.workout.id, workout.workout.workoutType) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            workout.runs.title?.let { Text(text = it) }
            Text(text = toDate(workout.workout.dateCreated))
        }
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Column () {
                Row(
                    modifier = Modifier
                    .padding(3.dp)) {
                    Box() {
                        Text("Distance:")
                    }
                    Box() {
                        Text(text = workout.runs.distanceMeters.toString() + unit.distanceLabel())
                    }
                }
                Row(
                    modifier = Modifier
                    .padding(3.dp)) {
                    Box() {
                        Text("Pace:")
                    }
                    Box() {
                        Text(text = getPace(workout.runs) + " ${unit.distanceLabel()}/min")
                    }
                }

            }
        }


    }

}


fun getPace(run: Run): String{
    val minutes = run.activeTime/60000
    val pace = minutes/run.distanceMeters
    return pace.toString()
}