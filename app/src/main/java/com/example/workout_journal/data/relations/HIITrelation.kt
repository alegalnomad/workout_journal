package com.example.workout_journal.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.workout_journal.data.entity.HIITExercise
import com.example.workout_journal.data.entity.HIITExerciseName
import com.example.workout_journal.data.entity.HIITrounds
import com.example.workout_journal.data.entity.Workout

data class HIITWithExercises(
    @Embedded val workout: Workout,

    @Relation(
        parentColumn = "id",
        entityColumn = "workout_id",
        entity = HIITrounds::class
    )
    val config: HIITrounds,

    @Relation(
        parentColumn = "id",
        entityColumn = "workout_id",
        entity = HIITExercise::class
    )
    val exercises: List<HIITExerciseWithName>
)

data class HIITExerciseWithName(
    @Embedded val exercise: HIITExercise,

    @Relation(
        parentColumn = "exercise_name_id",
        entityColumn = "id",
        entity = HIITExerciseName::class
    )
    val exerciseName: HIITExerciseName,

)


