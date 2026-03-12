package com.example.workout_journal.data.model

import androidx.room.*

@Entity(tableName = "weight_exercises")
data class WeightExercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)