package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.workout_journal.data.entity.WeightSet

@Dao
interface WeightSetDAO {
    @Insert
    suspend fun insertWeightSet(weightSet: WeightSet)

    @Update
    suspend fun updateWeightSet(weightSet: WeightSet)

    @Delete
    suspend fun deleteWeightSet(weightSet: WeightSet)
}