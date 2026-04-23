package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.workout_journal.data.entity.WeightSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightSetDAO {
    @Insert
    suspend fun insertWeightSet(weightSet: WeightSet): Long

    @Insert
    suspend fun insertAll(sets: List<WeightSet>)

    @Update
    suspend fun updateWeightSet(weightSet: WeightSet)

    @Delete
    suspend fun deleteWeightSet(weightSet: WeightSet)

    @Query("DELETE FROM weightSet WHERE weightExerciseId = :weightExerciseId")
    suspend fun deleteByExercise(weightExerciseId: Int)

    @Query("SELECT * FROM weightSet WHERE weightExerciseId = :weightExerciseId ORDER BY `set` ASC")
    fun getByExercise(weightExerciseId: Int): Flow<List<WeightSet>>

    @Query("SELECT * FROM weightSet WHERE weightExerciseId = :weightExerciseId ORDER BY `set` ASC")
    suspend fun getByExerciseOnce(weightExerciseId: Int): List<WeightSet>
}