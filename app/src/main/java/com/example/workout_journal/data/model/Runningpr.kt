package com.example.workout_journal.data.model

import androidx.room.*

@Entity(tableName = "running_prs")
data class RunningPr(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val distance: String,   // "1km", "1mi", "5km", "10km", "half", "marathon"
    val time: Float,        // stored in seconds
    val dateAchieved: Long = System.currentTimeMillis()
)