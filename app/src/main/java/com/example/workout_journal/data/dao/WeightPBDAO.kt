package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.workout_journal.data.entity.WeightPB
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightPBDAO {
    @Insert
    suspend fun insertWeightPB(weightPB: WeightPB)

    @Query("SELECT * FROM weight_pb WHERE exerciseNameId = :exerciseNameId ORDER BY bestWeightKg DESC LIMIT 1")
    suspend fun getCurrentPb(exerciseNameId: Long): WeightPB?

    @Query("SELECT * FROM weight_pb WHERE exerciseNameId = :exerciseNameId ORDER BY dateAchieved ASC")
    fun getHistory(exerciseNameId: Long): Flow<List<WeightPB>>

}