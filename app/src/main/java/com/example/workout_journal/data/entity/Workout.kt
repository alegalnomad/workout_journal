package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workoutType: WorkoutType,
    val dateCreated: Long = System.currentTimeMillis(),
)