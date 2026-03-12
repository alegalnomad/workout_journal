package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.HiitExerciseDao
import com.example.workout_journal.data.dao.HiitSessionDao
import com.example.workout_journal.data.dao.HiitSessionWithDetails
import com.example.workout_journal.data.model.HiitExercise
import com.example.workout_journal.data.model.HiitSession

class HiitRepository(
    private val hiitSessionDao: HiitSessionDao,
    private val hiitExerciseDao: HiitExerciseDao
) {

    // Sessions
    suspend fun insert(hiitSession: HiitSession): Long = hiitSessionDao.insert(hiitSession)

    suspend fun delete(hiitSession: HiitSession) = hiitSessionDao.delete(hiitSession)

    suspend fun getHiitSessionById(id: Int): HiitSession? =
        hiitSessionDao.getHiitSessionById(id)

    suspend fun getHiitSessionByWorkoutId(workoutId: Int): HiitSessionWithDetails? =
        hiitSessionDao.getHiitSessionByWorkoutId(workoutId)

    suspend fun getAllHiitSessions(): List<HiitSessionWithDetails> =
        hiitSessionDao.getAllHiitSessions()

    // Exercises (dropdown)
    suspend fun insertExercise(exercise: HiitExercise): Long = hiitExerciseDao.insert(exercise)

    suspend fun insertAllExercises(exercises: List<HiitExercise>) =
        hiitExerciseDao.insertAll(exercises)

    suspend fun deleteExercise(exercise: HiitExercise) = hiitExerciseDao.delete(exercise)

    suspend fun getAllExercises(): List<HiitExercise> = hiitExerciseDao.getAllExercises()

    suspend fun searchExercises(query: String): List<HiitExercise> =
        hiitExerciseDao.searchExercises(query)
}