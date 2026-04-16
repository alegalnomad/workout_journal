package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.workout_journal.data.entity.WeightExercise


@Dao
interface WeightExerciseDAO{
    @Insert
    suspend fun insertWeightExercise(weightExercise: WeightExercise)

    @Delete
    suspend fun deleteWeightExercise(weightExercise: WeightExercise)

    @Insert
    suspend fun insertAll(weightExercises: List<WeightExercise>)

    @Update
    suspend fun update(weightExercise: WeightExercise)

}