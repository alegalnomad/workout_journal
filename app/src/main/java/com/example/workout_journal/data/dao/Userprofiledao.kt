package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.UserProfile

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userProfile: UserProfile)

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getUserProfile(): UserProfile?

    @Query("UPDATE user_profile SET bodyWeight = :bodyWeight WHERE id = 1")
    suspend fun updateBodyWeight(bodyWeight: Float)

    @Query("UPDATE user_profile SET preferredWeightUnit = :unit WHERE id = 1")
    suspend fun updateWeightUnit(unit: String)

    @Query("UPDATE user_profile SET preferredDistanceUnit = :unit WHERE id = 1")
    suspend fun updateDistanceUnit(unit: String)

    @Query("UPDATE user_profile SET currentStreak = :currentStreak, longestStreak = :longestStreak, lastWorkoutDate = :lastWorkoutDate WHERE id = 1")
    suspend fun updateStreak(currentStreak: Int, longestStreak: Int, lastWorkoutDate: Long)
}