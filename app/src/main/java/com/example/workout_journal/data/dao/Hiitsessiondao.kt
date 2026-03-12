package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.HiitSession
import com.example.workout_journal.data.model.Workout

data class HiitSessionWithDetails(
    @Embedded val hiitSession: HiitSession,
    @Relation(parentColumn = "workoutId", entityColumn = "id")
    val workout: Workout
)

@Dao
interface HiitSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hiitSession: HiitSession): Long

    @Delete
    suspend fun delete(hiitSession: HiitSession)

    @Query("SELECT * FROM hiit_sessions WHERE id = :id")
    suspend fun getHiitSessionById(id: Int): HiitSession?

    @Transaction
    @Query("SELECT * FROM hiit_sessions WHERE workoutId = :workoutId")
    suspend fun getHiitSessionByWorkoutId(workoutId: Int): HiitSessionWithDetails?

    @Transaction
    @Query("SELECT * FROM hiit_sessions")
    suspend fun getAllHiitSessions(): List<HiitSessionWithDetails>
}