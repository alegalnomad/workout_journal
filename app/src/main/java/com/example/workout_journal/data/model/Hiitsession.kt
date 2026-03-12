package com.example.workout_journal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiit_sessions",
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HiitSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val workoutId: Int,
    val totalSets: Int,
    val totalRounds: Int,
    val totalTime: Float,
    val timePerRound: Float,
    val restAfterRound: Float,
    val exercises: List<String>
)