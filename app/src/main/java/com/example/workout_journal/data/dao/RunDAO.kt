package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.workout_journal.data.entity.Run
import com.example.workout_journal.data.relations.RunWithSplits

import kotlinx.coroutines.flow.Flow

@Dao
interface RunDAO {
    @Insert
    suspend fun insertRun(run: Run): Long

    @Delete
    suspend fun deleteRun(run: Run)

    @Transaction
    @Query("SELECT * FROM run WHERE id = :runId")
    fun getRunWithSplits(runId: Long): Flow<RunWithSplits>

    @Query("SELECT * FROM run ORDER BY timeElapsed ASC")
    fun getAllRuns(): Flow<List<Run>>

    @Query("SELECT * FROM run WHERE workoutId = :workoutId")
    suspend fun getByWorkout(workoutId: Long): Run?
}