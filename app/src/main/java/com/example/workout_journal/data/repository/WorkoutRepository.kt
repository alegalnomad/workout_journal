package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.WorkoutDAO
import com.example.workout_journal.data.entity.Workout
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.WorkoutWithHIIT
import com.example.workout_journal.data.relations.WorkoutWithRunning
import com.example.workout_journal.data.relations.WorkoutWithWeightExercises
import com.example.workout_journal.ui.viewmodel.WorkoutDetailResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val workoutDAO: WorkoutDAO)
{
    suspend fun insertWorkout(workout: Workout): Long = workoutDAO.insertWorkout(workout)


    suspend fun deleteWorkout(workout: Workout) {
        workoutDAO.deleteWorkout(workout)
    }

    fun getAllWorkouts(): Flow<List<Workout>> {
        return workoutDAO.getAll()
    }

    fun getWorkoutsByType(type: WorkoutType): Flow<List<Workout>> {
        return workoutDAO.getByType(type)
    }

    fun weightWorkouts(): Flow<List<WorkoutWithWeightExercises>> {
        return workoutDAO.getWorkoutsWithWeightExercises()
    }

    fun runWorkouts(): Flow<List<WorkoutWithRunning>> {
        return workoutDAO.getWorkoutsWithRunning()
    }

    fun hiitWorkouts(): Flow<List<WorkoutWithHIIT>> {
        return workoutDAO.getWorkoutsWithHiit()
    }

    fun getWeightWorkout(id: Long): Flow<WorkoutWithWeightExercises> {
        return workoutDAO.getWorkoutWithWeightExercises(id)
    }

    fun getRunWorkout(id: Long): Flow<WorkoutWithRunning> {
        return workoutDAO.getWorkoutWithRunning(id)
    }

    fun getHIITWorkout(id: Long): Flow<WorkoutWithHIIT> {
        return workoutDAO.getWorkoutWithHIIT(id)
    }
}