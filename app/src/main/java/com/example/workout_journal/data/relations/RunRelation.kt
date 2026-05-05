package com.example.workout_journal.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.workout_journal.data.entity.Run
import com.example.workout_journal.data.entity.RunSplits
import com.example.workout_journal.data.entity.Workout

data class WorkoutWithRunning(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId")
    val runs: Run
)

data class RunWithSplits(
    @Embedded val run: Run,
    @Relation(
        parentColumn = "id",
        entityColumn = "runId")
    val splits: List<RunSplits>
)