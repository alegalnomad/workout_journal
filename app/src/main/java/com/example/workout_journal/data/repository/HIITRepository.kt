package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.HIITExerciseDAO
import com.example.workout_journal.data.dao.HIITExerciseNameDAO
import com.example.workout_journal.data.dao.HIITSessionDAO
import com.example.workout_journal.data.entity.HIITExercise
import com.example.workout_journal.data.entity.HIITExerciseName
import com.example.workout_journal.data.entity.HIITSession
import javax.inject.Inject

class HIITRepository @Inject constructor(
    private val hiitSessionDAO: HIITSessionDAO,
    private val hiitExerciseDAO: HIITExerciseDAO,
    private val hiitExerciseNameDAO: HIITExerciseNameDAO
) {
    //HIIT Exercise Names
    fun getAllNames() = hiitExerciseNameDAO.getAll()
    fun searchNames(query: String) = hiitExerciseNameDAO.search(query)
    suspend fun addName(name: HIITExerciseName) = hiitExerciseNameDAO.insertHIITExerciseName(name)
    suspend fun deleteName(name: HIITExerciseName) = hiitExerciseNameDAO.deleteHIITExerciseName(name)
    suspend fun getName(id: Int) = hiitExerciseNameDAO.getName(id)

    //HIIT Exercises
    suspend fun addExercise(exercise: HIITExercise) = hiitExerciseDAO.insertHIITExercise(exercise)
    suspend fun deleteExercise(exercise: HIITExercise) = hiitExerciseDAO.deleteHIITExercise(exercise)

    //HIIT Session
    suspend fun addHIITSession(session: HIITSession) = hiitSessionDAO.insertHIITSession(session)
    suspend fun deleteHIITSession(session: HIITSession) = hiitSessionDAO.deleteHIITSession(session)

}