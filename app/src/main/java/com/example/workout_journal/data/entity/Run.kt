package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "run",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
            ],
    indices = [Index("workoutId")]
)
data class Run(
    @PrimaryKey(autoGenerate = true) val id : Long =0,
    val workoutId : Long,
    val title : String,
    val distanceMeters : Double,
    val timeElapsed : Long,
    val activeTime: Long,
    val elevationGain : Double,
    val elevationalLoss : Double,
    val notes : String,
    val polyPath: String,
)