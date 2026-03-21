package com.example.workout_journal.data.dao

import androidx.room.*
import com.example.workout_journal.data.model.WeightExercise
import com.example.workout_journal.data.model.WeightPr

data class WeightPrWithExercise(
    @Embedded val weightPr: WeightPr,
    @Relation(parentColumn = "exerciseId", entityColumn = "id")
    val exercise: WeightExercise
)

@Dao
interface WeightPrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weightPr: WeightPr): Long

    @Delete
    suspend fun delete(weightPr: WeightPr)

    @Transaction
    @Query("SELECT * FROM weight_prs WHERE exerciseId = :exerciseId ORDER BY weight DESC LIMIT 1")
    suspend fun getPrForExercise(exerciseId: Int): WeightPrWithExercise?

    @Transaction
    @Query("SELECT * FROM weight_prs WHERE exerciseId = :exerciseId ORDER BY dateAchieved DESC")
    suspend fun getAllPrsForExercise(exerciseId: Int): List<WeightPrWithExercise>

    @Transaction
    @Query("SELECT * FROM weight_prs ORDER BY dateAchieved DESC")
    suspend fun getAllPrs(): List<WeightPrWithExercise>
}