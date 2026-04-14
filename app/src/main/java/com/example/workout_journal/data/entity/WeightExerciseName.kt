package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercise_names")
data class WeightExerciseName(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    )