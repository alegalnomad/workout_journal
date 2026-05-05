package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.RunDAO
import com.example.workout_journal.data.dao.RunPBDAO
import com.example.workout_journal.data.dao.RunSplitsDAO
import com.example.workout_journal.data.dao.ShoesDAO
import com.example.workout_journal.data.datastore.UserPreferencesManager
import com.example.workout_journal.data.entity.Run
import com.example.workout_journal.data.entity.RunDistance
import com.example.workout_journal.data.entity.RunPB
import com.example.workout_journal.data.entity.RunSplits
import com.example.workout_journal.data.entity.Shoes
import javax.inject.Inject

class RunRepository @Inject constructor(
    private val runDAO: RunDAO,
    private val runSplitsDAO: RunSplitsDAO,
    private val runPbDAO: RunPBDAO,
    private val shoesDAO: ShoesDAO,
) {
    // RunDAO
    suspend fun insertRun(run: Run) = runDAO.insertRun(run)
    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)
    fun getRunWithSplits(runId: Long) = runDAO.getRunWithSplits(runId)
    fun getAllRuns() = runDAO.getAllRuns()



    // RunSplitsDAO
    suspend fun insertRunSplit(runSplit: RunSplits) = runSplitsDAO.insertRunSplit(runSplit)
    suspend fun saveRunSplits(runSplits: List<RunSplits>) = runSplitsDAO.insertAllRunSplits(runSplits)


    // RunPBDAO
    fun getAllRunPB() = runPbDAO.getAllRunPB()
    suspend fun getCurrentPB(distance: RunDistance) = runPbDAO.getCurrentPB(distance)
    fun getPBHistory(distance: RunDistance) = runPbDAO.getPBHistory(distance)

    suspend fun evaluateAndStorePBs(run: Run, runId: Long, splits: List<RunSplits>) {

        for (distance in RunDistance.entries) {
            when (distance) {
                RunDistance.LONGEST_RUN -> checkLongestRun(run, runId, splits)
                else                    -> checkContiguousWindow(distance, run, runId, splits)
            }
        }
    }
    private suspend fun checkContiguousWindow(
        distance: RunDistance,
        run     : Run,
        runId   : Long,
        splits  : List<RunSplits>
    ) {
        val windowSize = distance.km / 1_000
        if (splits.size < windowSize) return    // run wasn't long enough

        // Build the first window
        var windowSum       = splits.take(windowSize).sumOf { it.splitTime }
        var bestTimeMs      = windowSum
        var bestWindowStart = 0     // 0-based index into splits list

        // Slide across remaining windows
        for (i in 1..(splits.size - windowSize)) {
            windowSum = windowSum -
                    splits[i - 1].splitTime +
                    splits[i + windowSize - 1].splitTime
            if (windowSum < bestTimeMs) {
                bestTimeMs      = windowSum
                bestWindowStart = i
            }
        }

        val storedPb = runPbDAO.getCurrentPB(distance)
        val isNewPb  = storedPb == null || bestTimeMs < storedPb.timeElapsed

        if (isNewPb) {
            runPbDAO.insertRunPB(
                RunPB(
                    runId       = runId,
                    workoutId   = run.workoutId,
                    distance    = distance,
                    timeElapsed = bestTimeMs,
                    windowStart = splits[bestWindowStart].splitIndex,
                    windowEnd   = splits[bestWindowStart + windowSize - 1].splitIndex
                )
            )
        }
    }
    private suspend fun checkLongestRun(
        run   : Run,
        runId : Long,
        splits: List<RunSplits>
    ) {
        val storedPb = runPbDAO.getCurrentPB(RunDistance.LONGEST_RUN)
        val isNewPb  = storedPb == null ||
                run.distanceMeters > (storedPb.totalDistanceMeters ?: 0.0)

        if (isNewPb) {
            runPbDAO.insertRunPB(
                RunPB(
                    runId               = runId,
                    workoutId           = run.workoutId,
                    distance            = RunDistance.LONGEST_RUN,
                    timeElapsed         = run.timeElapsed,
                    windowStart         = splits.firstOrNull()?.splitIndex ?: 1,
                    windowEnd           = splits.lastOrNull()?.splitIndex  ?: 1,
                    totalDistanceMeters = run.distanceMeters
                )
            )
        }
    }

    // Shoes DAO

    suspend fun addShoe(shoes: Shoes) = shoesDAO.insertShoes(shoes)
    suspend fun updateShoe(shoes: Shoes) = shoesDAO.updateShoes(shoes)
    suspend fun fetchShoes(id: Int) = shoesDAO.fetchShoeDetails(id)

}