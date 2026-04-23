package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.workout_journal.data.entity.HIITExerciseName
import kotlinx.coroutines.flow.Flow

@Dao
interface HIITExerciseNameDAO {
    @Insert
    suspend fun insertHIITExerciseName(exerciseName: HIITExerciseName)

    @Delete
    suspend fun deleteHIITExerciseName(exerciseName: HIITExerciseName)

    @Query("SELECT * FROM HIITEXERCISENAMES ORDER BY name ASC")
    fun getAll(): Flow<List<HIITExerciseName>>

    @Query("SELECT * FROM HIITEXERCISENAMES WHERE name LIKE :query || '%'")
    suspend fun search(query: String): List<HIITExerciseName>


}