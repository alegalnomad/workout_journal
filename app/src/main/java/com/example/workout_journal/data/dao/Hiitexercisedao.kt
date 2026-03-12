package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.HiitExercise

@Dao
interface HiitExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: HiitExercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<HiitExercise>)

    @Delete
    suspend fun delete(exercise: HiitExercise)

    @Query("SELECT * FROM hiit_exercises ORDER BY name ASC")
    suspend fun getAllExercises(): List<HiitExercise>

    @Query("SELECT * FROM hiit_exercises WHERE id = :id")
    suspend fun getExerciseById(id: Int): HiitExercise?

    @Query("SELECT * FROM hiit_exercises WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun searchExercises(query: String): List<HiitExercise>
}