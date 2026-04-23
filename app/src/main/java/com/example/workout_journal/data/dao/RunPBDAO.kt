package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.workout_journal.data.entity.RunDistance
import com.example.workout_journal.data.entity.RunPB
import kotlinx.coroutines.flow.Flow

@Dao
interface RunPBDAO {
    @Insert
    suspend fun insertRunPB(runPB: RunPB): Long

    @Transaction
    @Query("""
    SELECT * FROM runPB 
    WHERE (distance, timeElapsed) IN (
        SELECT distance, MIN(timeElapsed) 
        FROM runPB 
        GROUP BY distance
    )
    ORDER BY 
        CASE distance 
            WHEN 'ONE_KM' THEN 1
            WHEN 'FIVE_KM' THEN 2
            WHEN 'TEN_KM' THEN 3
            WHEN 'HALF_MARATHON' THEN 4
            WHEN 'FULL_MARATHON' THEN 5
            WHEN 'LONGEST_RUN' THEN 6
            ELSE 7 
        END ASC,
        dateCreated DESC
""")
    fun getAllRunPB(): Flow<List<RunPB>>

    @Query("""
    SELECT * FROM runPB 
    WHERE distance = :distance 
    ORDER BY timeElapsed ASC 
    LIMIT 1
""")
    suspend fun getCurrentPB(distance: RunDistance): RunPB?

    @Query("""
        SELECT * FROM runPB
        WHERE distance = :distance
        ORDER BY dateCreated ASC
    """)
    fun getPBHistory(distance: RunDistance): Flow<List<RunPB>>
}