package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "runPB",
    foreignKeys = [
        ForeignKey(
            entity = Run::class,
            parentColumns = ["id"],
            childColumns = ["runId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
],
    indices = [Index("runId"), Index("workoutId")]

        )
data class RunPB(
    @PrimaryKey(autoGenerate = true) val id : Long =0,
    val runId : Long,
    val workoutId : Long,
    val distance : RunDistance,
    val timeElapsed : Long,
    val dateCreated : Long = System.currentTimeMillis()
)