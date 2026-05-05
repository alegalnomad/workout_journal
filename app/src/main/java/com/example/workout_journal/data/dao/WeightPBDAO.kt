package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.workout_journal.data.entity.WeightPB
import com.example.workout_journal.data.relations.WeightPbWithNames
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightPBDAO {
    @Insert
    suspend fun insertWeightPB(weightPB: WeightPB): Long


    @Transaction
    @Query("SELECT * FROM weight_pb WHERE exerciseNameId = :exerciseNameId ORDER BY weightKg DESC LIMIT 1")
    suspend fun getCurrentPb(exerciseNameId: Int): WeightPbWithNames?

    @Transaction
    @Query("SELECT * FROM weight_pb WHERE exerciseNameId = :exerciseNameId ORDER BY dateAchieved ASC")
    fun getHistory(exerciseNameId: Int): Flow<List<WeightPbWithNames>>

    @Transaction
    @Query("""
        SELECT * FROM weight_pb pb1
        WHERE weightKg = (
            SELECT MAX(pb2.weightKg)
            FROM weight_pb pb2
            WHERE pb2.exerciseNameId = pb1.exerciseNameId
        )
        GROUP BY exerciseNameId
    """)
    fun getAllCurrentPbs(): Flow<List<WeightPbWithNames>>

}