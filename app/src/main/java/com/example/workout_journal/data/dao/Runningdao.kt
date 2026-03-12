package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.Running
import com.example.workout_journal.data.model.RunningSplit
import com.example.workout_journal.data.model.Workout

data class RunningWithDetails(
    @Embedded val running: Running,
    @Relation(parentColumn = "workoutId", entityColumn = "id")
    val workout: Workout,
    @Relation(parentColumn = "id", entityColumn = "runningId")
    val splits: List<RunningSplit>
)

@Dao
interface RunningDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(running: Running): Long

    @Delete
    suspend fun delete(running: Running)

    @Query("SELECT * FROM running WHERE id = :id")
    suspend fun getRunningById(id: Int): Running?

    @Transaction
    @Query("SELECT * FROM running WHERE workoutId = :workoutId")
    suspend fun getRunningByWorkoutId(workoutId: Int): RunningWithDetails?

    @Transaction
    @Query("SELECT * FROM running")
    suspend fun getAllRunningSessions(): List<RunningWithDetails>
}