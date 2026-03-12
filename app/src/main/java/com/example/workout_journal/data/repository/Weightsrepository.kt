package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.WeightExerciseDao
import com.example.workout_journal.data.dao.WeightSetDao
import com.example.workout_journal.data.dao.WeightSetWithDetails
import com.example.workout_journal.data.model.WeightExercise
import com.example.workout_journal.data.model.WeightSet

class WeightsRepository(
    private val weightSetDao: WeightSetDao,
    private val weightExerciseDao: WeightExerciseDao
) {

    // Weight sets
    suspend fun insert(weightSet: WeightSet): Long = weightSetDao.insert(weightSet)

    suspend fun insertAll(weightSets: List<WeightSet>) = weightSetDao.insertAll(weightSets)

    suspend fun delete(weightSet: WeightSet) = weightSetDao.delete(weightSet)

    suspend fun getWeightSetById(id: Int): WeightSet? = weightSetDao.getWeightSetById(id)

    suspend fun getWeightSetsByWorkoutId(workoutId: Int): List<WeightSetWithDetails> =
        weightSetDao.getWeightSetsByWorkoutId(workoutId)

    suspend fun getAllWeightSets(): List<WeightSetWithDetails> = weightSetDao.getAllWeightSets()

    suspend fun deleteAllSetsForWorkout(workoutId: Int) =
        weightSetDao.deleteAllSetsForWorkout(workoutId)

    // Exercises (dropdown)
    suspend fun insertExercise(exercise: WeightExercise): Long = weightExerciseDao.insert(exercise)

    suspend fun insertAllExercises(exercises: List<WeightExercise>) =
        weightExerciseDao.insertAll(exercises)

    suspend fun deleteExercise(exercise: WeightExercise) = weightExerciseDao.delete(exercise)

    suspend fun getAllExercises(): List<WeightExercise> = weightExerciseDao.getAllExercises()

    suspend fun searchExercises(query: String): List<WeightExercise> =
        weightExerciseDao.searchExercises(query)
}