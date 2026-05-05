package com.example.workout_journal.ui.cards

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.WorkoutWithHIIT
import com.example.workout_journal.utils.toDate

@Composable
fun HIITWorkoutCard(
    workout: WorkoutWithHIIT,
    onWorkoutSelect: (Long, WorkoutType) -> Unit,
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
            Text(text = "HIIT Session")
            Text(text = toDate(workout.workout.dateCreated))
        }
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min))
            {
                Box() {
                    Text(text = "Sets: ${workout.config.rounds.sets}")
                }

                VerticalDivider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)
                )

                Box() {
                    Text(text = "Rounds: ${workout.config.rounds.rounds}")
                }

                VerticalDivider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)
                )

                Box() {
                    Text(text = "Work Time: ${workout.config.rounds.roundDuration}")
                }

                VerticalDivider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)
                )

                Box() {
                    Text(text = "Rest Time: ${workout.config.rounds.restDuration}")
                }
            }
            HorizontalDivider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            Text(text = "Exercises :")

            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                workout.config.exercises.forEach { (exercise, exerciseName) ->
                    Text(text = exerciseName.name)
                }
            }
        }
    }
}

