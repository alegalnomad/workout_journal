package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.WeightExercise

@Dao
interface WeightExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: WeightExercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<WeightExercise>)

    @Delete
    suspend fun delete(exercise: WeightExercise)

    @Query("SELECT * FROM weight_exercises ORDER BY name ASC")
    suspend fun getAllExercises(): List<WeightExercise>

    @Query("SELECT * FROM weight_exercises WHERE id = :id")
    suspend fun getExerciseById(id: Int): WeightExercise?

    @Query("SELECT * FROM weight_exercises WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun searchExercises(query: String): List<WeightExercise>
}