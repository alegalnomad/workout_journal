package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.workout_journal.data.entity.BodyHeightWeight
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyHeightWeightDAO {
    @Insert
    suspend fun insertBodyHeightWeight(bodyHeightWeight: BodyHeightWeight)

    @Update
    suspend fun updateBodyHeightWeight(bodyHeightWeight: BodyHeightWeight)

    @Delete
    suspend fun deleteBodyHeightWeight(bodyHeightWeight: BodyHeightWeight)

    @Query("SELECT * FROM body_height_weight ORDER BY dateCreated DESC LIMIT 1")
    suspend fun getLatest(): BodyHeightWeight?

    @Query("SELECT * FROM body_height_weight ORDER BY dateCreated DESC")
    fun getAll(): Flow<List<BodyHeightWeight>>

}