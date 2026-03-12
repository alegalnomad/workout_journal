package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.WorkoutDao
import com.example.workout_journal.data.model.Workout

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    suspend fun insert(workout: Workout): Long = workoutDao.insert(workout)

    suspend fun delete(workout: Workout) = workoutDao.delete(workout)

    suspend fun getAllWorkouts(): List<Workout> = workoutDao.getAllWorkouts()

    suspend fun getWorkoutById(id: Int): Workout? = workoutDao.getWorkoutById(id)

    suspend fun getWorkoutsByType(type: String): List<Workout> = workoutDao.getWorkoutsByType(type)
}