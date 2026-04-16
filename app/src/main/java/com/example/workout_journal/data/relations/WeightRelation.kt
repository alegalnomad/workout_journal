package com.example.workout_journal.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.workout_journal.data.entity.WeightExercise
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.data.entity.WeightSet
import com.example.workout_journal.data.entity.Workout

data class WorkoutWithWeightExercises(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId",
        entity = WeightExercise::class
    )
    val exercises: List<WeightExerciseWithSets>

)

data class WeightExerciseWithSets(
    @Embedded val exercise: WeightExercise,

    @Relation(
        parentColumn = "exerciseNameId",
        entityColumn = "id",
        entity = WeightExerciseName::class
    )
    val exerciseName: WeightExerciseName,


    @Relation(
        parentColumn = "id",
        entityColumn = "weightExerciseId",
        entity = WeightSet::class
    )
    val sets: List<WeightSet>
)