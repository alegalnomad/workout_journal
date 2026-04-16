package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.workout_journal.data.entity.HIITRounds

@Dao
interface HIITRoundsDAO {
    @Insert
    suspend fun insertHIITRounds(hiitRounds: HIITRounds)
}