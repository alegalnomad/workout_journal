package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.workout_journal.data.entity.WeightExerciseName
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightExerciseNameDAO {
    @Insert
    suspend fun insertWeightExerciseName(weightExerciseName: WeightExerciseName): Long

    @Update
    suspend fun updateWeightExerciseName(weightExerciseName: WeightExerciseName)

    @Delete
    suspend fun deleteWeightExerciseName(name: WeightExerciseName)

    @Query("SELECT * FROM weightExerciseNames ORDER BY name ASC")
    fun getAll(): Flow<List<WeightExerciseName>>

    @Query("SELECT * FROM weightExerciseNames WHERE name LIKE :query || '%'")
    suspend fun search(query: String): List<WeightExerciseName>

    @Query("SELECT * FROM weightExerciseNames WHERE id = :id")
    suspend fun selectedExercise(id: Int): WeightExerciseName?
}