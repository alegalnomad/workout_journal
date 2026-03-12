package com.example.workout_journal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val type: String
)