package com.example.workout_journal.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.workout_journal.data.entity.HIITExercise
import com.example.workout_journal.data.entity.HIITExerciseName
import com.example.workout_journal.data.entity.HIITRounds
import com.example.workout_journal.data.entity.Workout

data class HIITWithExercises(
    @Embedded val workout: Workout,

    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId",
        entity = HIITRounds::class
    )
    val config: HIITRounds,

    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId",
        entity = HIITExercise::class
    )
    val exercises: List<HIITExerciseWithName>
)

data class HIITExerciseWithName(
    @Embedded val exercise: HIITExercise,

    @Relation(
        parentColumn = "exerciseNameId",
        entityColumn = "id",
        entity = HIITExerciseName::class
    )
    val exerciseName: HIITExerciseName,

)


