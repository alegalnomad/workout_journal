package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.WeightExerciseDAO
import com.example.workout_journal.data.dao.WeightExerciseNameDAO
import com.example.workout_journal.data.dao.WeightPBDAO
import com.example.workout_journal.data.dao.WeightSetDAO
import com.example.workout_journal.data.entity.WeightExercise
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.data.entity.WeightPB
import com.example.workout_journal.data.entity.WeightSet
import com.example.workout_journal.data.relations.WeightExerciseWithSets
import com.example.workout_journal.data.relations.WeightPbWithNames
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeightRepository @Inject constructor(
    private val weightExerciseNameDAO: WeightExerciseNameDAO,
    private val weightExerciseDAO: WeightExerciseDAO,
    private val weightSetDAO: WeightSetDAO,
    private val weightPBDAO: WeightPBDAO,

)  {
    //WeightExerciseName
    val allNames: Flow<List<WeightExerciseName>> = weightExerciseNameDAO.getAll()
    suspend fun addName(name: WeightExerciseName) = weightExerciseNameDAO.insertWeightExerciseName(name)
    suspend fun deleteName(name: WeightExerciseName) = weightExerciseNameDAO.deleteWeightExerciseName(name)
    suspend fun searchName(query: String) = weightExerciseNameDAO.search(query)

    //WeightExercise
    suspend fun addExercise(exercise: WeightExercise) = weightExerciseDAO.insertWeightExercise(exercise)
    suspend fun deleteExercise(exercise: WeightExercise) = weightExerciseDAO.deleteWeightExercise(exercise)
    suspend fun updateExercise(exercise: WeightExercise) = weightExerciseDAO.updateWeightExercise(exercise)
    suspend fun addExercises(exercises: List<WeightExercise>) = weightExerciseDAO.insertAllWeightExercises(exercises)

    //WeightSet
    suspend fun addSet(set: WeightSet) = weightSetDAO.insertWeightSet(set)
    suspend fun addSets(sets: List<WeightSet>) = weightSetDAO.insertAll(sets)
    suspend fun updateSet(set: WeightSet) = weightSetDAO.updateWeightSet(set)
    suspend fun deleteSet(set: WeightSet) = weightSetDAO.deleteWeightSet(set)
    suspend fun deleteByExercise(weightExerciseId: Int) = weightSetDAO.deleteByExercise(weightExerciseId)
    fun setsForExercise(weightExerciseId: Int): Flow<List<WeightSet>> =weightSetDAO.getByExercise(weightExerciseId)

    //WeightPB
    suspend fun getCurrentPb(exerciseNameId: Int) = weightPBDAO.getCurrentPb(exerciseNameId)
    fun allCurrentPbs(): Flow<List<WeightPbWithNames>> = weightPBDAO.getAllCurrentPbs()
    fun pbHistoryForExercise(exerciseNameId: Int): Flow<List<WeightPbWithNames>> = weightPBDAO.getHistory(exerciseNameId)

    suspend fun refreshPrsForWorkout(workoutId: Long) {
        val exercises = weightExerciseDAO.getByWorkoutOnce(workoutId)
        evaluateAndStorePrs(exercises)
    }

    suspend fun evaluateAndStorePrs(exercises: List<WeightExerciseWithSets>) {
        for (exerciseWithSets in exercises) {
            val exerciseNameId = exerciseWithSets.exercise.exerciseNameId
            val sets           = exerciseWithSets.sets

            if (sets.isEmpty()) continue

            // Heaviest set in this exercise entry
            val bestSet = sets.maxByOrNull { it.weightKg } ?: continue

            // Compare against stored PR
            val currentPb = weightPBDAO.getCurrentPb(exerciseNameId)
            val isNewPr   = currentPb == null || bestSet.weightKg > currentPb.pb.weightKg

            if (isNewPr) {
                weightPBDAO.insertWeightPB(
                    WeightPB(
                        exerciseNameId   = exerciseNameId,
                        weightExerciseId = exerciseWithSets.exercise.id,
                        weightKg         = bestSet.weightKg
                    )
                )
            }
        }
    }
}