package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.workout_journal.data.entity.Shoes

@Dao
interface ShoesDAO {
    @Insert
    suspend fun insertShoes(shoes: Shoes): Long

    @Update
    suspend fun updateShoes(shoes: Shoes)

    @Query("SELECT * FROM shoes WHERE id = :id")
    suspend fun fetchShoeDetails(id: Int) : Shoes
}