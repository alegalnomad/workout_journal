package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiitExerciseNames",
)
data class HIITExerciseName(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
)