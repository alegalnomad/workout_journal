package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.workout_journal.data.entity.WeightExercise
import com.example.workout_journal.data.relations.WeightExerciseWithSets
import kotlinx.coroutines.flow.Flow


@Dao
interface WeightExerciseDAO{
    @Insert
    suspend fun insertWeightExercise(weightExercise: WeightExercise): Long

    @Delete
    suspend fun deleteWeightExercise(weightExercise: WeightExercise)

    @Insert
    suspend fun insertAllWeightExercises(weightExercises: List<WeightExercise>)

    @Update
    suspend fun updateWeightExercise(weightExercise: WeightExercise)

    @Transaction
    @Query("SELECT * FROM workoutExercise WHERE workoutId = :workoutId")
    fun getByWorkout(workoutId: Long): Flow<List<WeightExerciseWithSets>>

    // One-shot — used by PR evaluation right after a workout is saved
    @Transaction
    @Query("SELECT * FROM workoutExercise WHERE workoutId = :workoutId")
    suspend fun getByWorkoutOnce(workoutId: Long): List<WeightExerciseWithSets>

    // One-shot — fetch a single exercise with its sets (e.g. for edit screen)
    @Transaction
    @Query("SELECT * FROM workoutExercise WHERE id = :exerciseId")
    suspend fun getByIdOnce(exerciseId: Long): WeightExerciseWithSets?

}