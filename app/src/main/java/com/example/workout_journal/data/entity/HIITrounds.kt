package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiitRounds",
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
data class HIITRounds(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workoutId: Long,
    val rounds: Int,
    val sets: Int,
    val roundDuration: Long,
    val restDuration: Long,
    val setRest: Long,
    )