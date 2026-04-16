package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.workout_journal.data.entity.Workout
import com.example.workout_journal.data.entity.WorkoutType
import com.example.workout_journal.data.relations.HIITWithExercises
import com.example.workout_journal.data.relations.WorkoutWithRunning
import com.example.workout_journal.data.relations.WorkoutWithWeightExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDAO{
    @Insert
    suspend fun insertWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workout ORDER BY dateCreated DESC")
    fun getAll(): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE workoutType = :type ORDER BY dateCreated DESC")
    fun getByType(type: WorkoutType): Flow<List<Workout>>
    @Transaction
    @Query("SELECT * FROM workout WHERE workoutType = 'WEIGHTS' ORDER BY dateCreated DESC")
    fun getWorkoutsWithWeightExercises(): Flow<List<WorkoutWithWeightExercises>>

    @Transaction
    @Query("SELECT * FROM workout WHERE workoutType = 'RUN' ORDER BY dateCreated DESC")
    fun getWorkoutsWithRunning(): Flow<List<WorkoutWithRunning>>

    @Transaction
    @Query("SELECT * FROM workout WHERE workoutType = 'HIIT' ORDER BY dateCreated DESC")
    fun getWorkoutsWithHiit(): Flow<List<HIITWithExercises>>

}