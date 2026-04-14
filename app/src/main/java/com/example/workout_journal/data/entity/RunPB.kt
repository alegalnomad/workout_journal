package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "run_pb",
    foreignKeys = [
        ForeignKey(
            entity = Run::class,
            parentColumns = ["id"],
            childColumns = ["run_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE
        )
],
    indices = [Index("run_id"), Index("workout_id")]

        )
data class RunPB(
    @PrimaryKey(autoGenerate = true) val id : Int =0,
    val run_id : Int,
    val workout_id : Int,
    val distance : RunDistance,
    val time_elapsed : Long,
)