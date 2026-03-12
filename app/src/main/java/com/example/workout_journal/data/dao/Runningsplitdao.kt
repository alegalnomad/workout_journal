package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.RunningSplit

@Dao
interface RunningSplitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(split: RunningSplit): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(splits: List<RunningSplit>)

    @Delete
    suspend fun delete(split: RunningSplit)

    @Query("SELECT * FROM running_splits WHERE runningId = :runningId ORDER BY kilometre ASC")
    suspend fun getSplitsForRun(runningId: Int): List<RunningSplit>

    @Query("DELETE FROM running_splits WHERE runningId = :runningId")
    suspend fun deleteSplitsForRun(runningId: Int)
}