package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiit_rounds",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("exercise_id")]
)
data class HIITrounds(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val workout_id: Int,
    val round: Int,
    val set: Int,
    val round_duration: Long,
    val rest_duration: Long,
    val set_rest: Long,
    )