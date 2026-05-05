package com.example.workout_journal.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.SetType
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.WeightExerciseWithSets
import com.example.workout_journal.data.relations.WorkoutWithWeightExercises
import com.example.workout_journal.utils.toDate
import com.example.workout_journal.utils.weightLabel

@Composable
fun WeightWorkoutCard(
    workout: WorkoutWithWeightExercises,
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
            Text(text = "Weight Session")
            Text(text = toDate(workout.workout.dateCreated))
        }
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Working Sets:")
            Column() {
                getExerciseMap(workout, unit).forEach { (exerciseName, setLists) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(150.dp),
                        ) {
                            Text(text = "$exerciseName :")
                        }
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            setLists.forEach {
                                Text(text = it)
                            }
                        }
                    }


                }
            }


        }

    }
}

fun getExerciseMap(
    workout: WorkoutWithWeightExercises,
    unit: MeasureUnit
): Map<String, List<String>> {
    val exerciseMap: MutableMap<String, List<String>> =
        mutableMapOf<String, List<String>>().toMutableMap()
    for (exercise in workout.exercises) {
        val exerciseName: String = exercise.exerciseName.name
        val sets: List<String> = getSets(exercise, unit)
        if (sets.isNotEmpty()) {
            exerciseMap += exerciseName to sets
        }
    }
    return exerciseMap
}

fun getSets(exercise: WeightExerciseWithSets, unit: MeasureUnit): List<String> {
    var setList: List<String> = mutableListOf()
    for (set in exercise.sets) {
        if (set.setType == SetType.WORKING) {
            val reps = set.reps
            val weight = set.weightKg
            val setString = "$weight ${unit.weightLabel()} x $reps"
            setList = setList + setString
        }
    }
    return setList

}
