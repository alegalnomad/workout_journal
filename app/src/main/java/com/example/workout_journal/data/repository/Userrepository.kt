package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.RunningPrDao
import com.example.workout_journal.data.dao.UserProfileDao
import com.example.workout_journal.data.dao.WeightPrDao
import com.example.workout_journal.data.dao.WeightPrWithExercise
import com.example.workout_journal.data.model.RunningPr
import com.example.workout_journal.data.model.UserProfile
import com.example.workout_journal.data.model.WeightPr

class UserRepository(
    private val userProfileDao: UserProfileDao,
    private val weightPrDao: WeightPrDao,
    private val runningPrDao: RunningPrDao
) {

    // --- User Profile ---
    suspend fun getUserProfile(): UserProfile? = userProfileDao.getUserProfile()

    suspend fun insert(userProfile: UserProfile) = userProfileDao.insert(userProfile)

    suspend fun updateBodyWeight(bodyWeight: Float) =
        userProfileDao.updateBodyWeight(bodyWeight)

    suspend fun updateWeightUnit(unit: String) = userProfileDao.updateWeightUnit(unit)

    suspend fun updateDistanceUnit(unit: String) = userProfileDao.updateDistanceUnit(unit)

    suspend fun updateStreak(profile: UserProfile) {
        val today = System.currentTimeMillis()
        val oneDayMillis = 86_400_000L

        val daysSinceLastWorkout = if (profile.lastWorkoutDate != null) {
            (today - profile.lastWorkoutDate) / oneDayMillis
        } else {
            2L
        }

        val newStreak = when {
            daysSinceLastWorkout <= 1L -> profile.currentStreak + 1
            else -> 1
        }

        val newLongest = maxOf(newStreak, profile.longestStreak)
        userProfileDao.updateStreak(newStreak, newLongest, today)
    }

    // --- Weight PRs ---
    suspend fun insertWeightPr(weightPr: WeightPr): Long = weightPrDao.insert(weightPr)

    suspend fun deleteWeightPr(weightPr: WeightPr) = weightPrDao.delete(weightPr)

    suspend fun getPrForExercise(exerciseId: Int): WeightPrWithExercise? =
        weightPrDao.getPrForExercise(exerciseId)

    suspend fun getAllPrsForExercise(exerciseId: Int): List<WeightPrWithExercise> =
        weightPrDao.getAllPrsForExercise(exerciseId)

    suspend fun getAllWeightPrs(): List<WeightPrWithExercise> = weightPrDao.getAllPrs()

    // --- Running PRs ---
    suspend fun insertRunningPr(runningPr: RunningPr): Long = runningPrDao.insert(runningPr)

    suspend fun deleteRunningPr(runningPr: RunningPr) = runningPrDao.delete(runningPr)

    suspend fun getPrForDistance(distance: String): RunningPr? =
        runningPrDao.getPrForDistance(distance)

    suspend fun getAllPrsForDistance(distance: String): List<RunningPr> =
        runningPrDao.getAllPrsForDistance(distance)

    suspend fun getAllRunningPrs(): List<RunningPr> = runningPrDao.getAllPrs()

    // --- Unit conversion helpers ---
    fun convertWeight(value: Float, unit: String): Float {
        return if (unit == "lbs") value * 2.20462f else value
    }

    fun convertDistance(value: Float, unit: String): Float {
        return if (unit == "mi") value * 0.621371f else value
    }
}