package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.workout_journal.data.entity.HIITExercise

@Dao
interface HIITExerciseDAO {
    @Insert
    suspend fun insertHIITExercise(exercise: HIITExercise)

    @Delete
    suspend fun deleteHIITExercise(exercise: HIITExercise)

}