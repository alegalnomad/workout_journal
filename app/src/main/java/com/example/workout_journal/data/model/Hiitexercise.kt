package com.example.workout_journal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hiit_exercises")
data class HiitExercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)