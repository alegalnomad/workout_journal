package com.example.workout_journal.data.model

import androidx.room.*

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val bodyWeight: Float? = null,
    val preferredWeightUnit: String = "kg",
    val preferredDistanceUnit: String = "km",
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastWorkoutDate: Long? = null
)