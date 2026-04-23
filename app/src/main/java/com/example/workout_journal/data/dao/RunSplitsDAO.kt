package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workout_journal.data.entity.RunSplits
import kotlinx.coroutines.flow.Flow

@Dao
interface RunSplitsDAO {
    @Insert
    suspend fun insertRunSplit(runSplit: RunSplits): Long

    @Insert
    suspend fun insertAllRunSplits(runSplits: List<RunSplits>)

    @Query("SELECT * FROM runSplits WHERE runId = :runId ORDER BY splitIndex ASC")
    suspend fun getSplitsOnce(runId: Long): List<RunSplits>

    @Query("SELECT * FROM runSplits WHERE runId = :runningId ORDER BY splitIndex ASC")
    fun observeByRun(runningId: Long): Flow<List<RunSplits>>
}

