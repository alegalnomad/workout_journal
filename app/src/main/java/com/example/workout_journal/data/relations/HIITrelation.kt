package com.example.workout_journal.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.workout_journal.data.entity.HIITExercise
import com.example.workout_journal.data.entity.HIITExerciseName
import com.example.workout_journal.data.entity.HIITSession
import com.example.workout_journal.data.entity.Workout

data class WorkoutWithHIIT(
    @Embedded val workout: Workout,

    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId",
        entity = HIITSession::class
    )
    val config: HIITRoundsWithExercises
)

data class HIITRoundsWithExercises(
    @Embedded val rounds: HIITSession,

    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId",
        entity = HIITExercise::class
    )
    val exercises: List<HIITRoundsExerciseWithName>
)

data class HIITRoundsExerciseWithName(
    @Embedded val exercise: HIITExercise,

    @Relation(
        parentColumn = "exerciseNameId",
        entityColumn = "id",
        entity = HIITExerciseName::class
    )
    val exerciseName: HIITExerciseName
)


