package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.RunningDao
import com.example.workout_journal.data.dao.RunningSplitDao
import com.example.workout_journal.data.dao.RunningWithDetails
import com.example.workout_journal.data.model.Running
import com.example.workout_journal.data.model.RunningSplit

class RunningRepository(
    private val runningDao: RunningDao,
    private val runningSplitDao: RunningSplitDao
) {

    suspend fun insert(running: Running): Long = runningDao.insert(running)

    suspend fun delete(running: Running) = runningDao.delete(running)

    suspend fun getRunningById(id: Int): Running? = runningDao.getRunningById(id)

    suspend fun getRunningByWorkoutId(workoutId: Int): RunningWithDetails? =
        runningDao.getRunningByWorkoutId(workoutId)

    suspend fun getAllRunningSessions(): List<RunningWithDetails> =
        runningDao.getAllRunningSessions()

    // Splits
    suspend fun insertSplit(split: RunningSplit): Long = runningSplitDao.insert(split)

    suspend fun insertAllSplits(splits: List<RunningSplit>) = runningSplitDao.insertAll(splits)

    suspend fun getSplitsForRun(runningId: Int): List<RunningSplit> =
        runningSplitDao.getSplitsForRun(runningId)

    suspend fun deleteSplitsForRun(runningId: Int) = runningSplitDao.deleteSplitsForRun(runningId)
}