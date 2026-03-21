package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.RunningPr

@Dao
interface RunningPrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(runningPr: RunningPr): Long

    @Delete
    suspend fun delete(runningPr: RunningPr)

    @Query("SELECT * FROM running_prs WHERE distance = :distance ORDER BY time ASC LIMIT 1")
    suspend fun getPrForDistance(distance: String): RunningPr?

    @Query("SELECT * FROM running_prs WHERE distance = :distance ORDER BY dateAchieved DESC")
    suspend fun getAllPrsForDistance(distance: String): List<RunningPr>

    @Query("SELECT * FROM running_prs ORDER BY dateAchieved DESC")
    suspend fun getAllPrs(): List<RunningPr>
}