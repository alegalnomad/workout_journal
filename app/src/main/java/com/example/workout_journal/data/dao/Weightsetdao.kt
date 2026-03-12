package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.WeightSet
import com.example.workout_journal.data.model.Workout
import com.example.workout_journal.data.model.WeightExercise

data class WeightSetWithDetails(
    @Embedded val weightSet: WeightSet,
    @Relation(parentColumn = "workoutId", entityColumn = "id")
    val workout: Workout,
    @Relation(parentColumn = "exerciseId", entityColumn = "id")
    val exercise: WeightExercise
)

@Dao
interface WeightSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weightSet: WeightSet): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weightSets: List<WeightSet>)

    @Delete
    suspend fun delete(weightSet: WeightSet)

    @Query("SELECT * FROM weights WHERE id = :id")
    suspend fun getWeightSetById(id: Int): WeightSet?

    @Transaction
    @Query("SELECT * FROM weights WHERE workoutId = :workoutId ORDER BY setNumber ASC")
    suspend fun getWeightSetsByWorkoutId(workoutId: Int): List<WeightSetWithDetails>

    @Transaction
    @Query("SELECT * FROM weights")
    suspend fun getAllWeightSets(): List<WeightSetWithDetails>

    @Query("DELETE FROM weights WHERE workoutId = :workoutId")
    suspend fun deleteAllSetsForWorkout(workoutId: Int)
}