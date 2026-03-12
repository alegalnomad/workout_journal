package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.Workout

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout): Long

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * FROM workouts ORDER BY createdAt DESC")
    suspend fun getAllWorkouts(): List<Workout>

    @Query("SELECT * FROM workouts WHERE id = :id")
    suspend fun getWorkoutById(id: Int): Workout?

    @Query("SELECT * FROM workouts WHERE type = :type ORDER BY createdAt DESC")
    suspend fun getWorkoutsByType(type: String): List<Workout>
}