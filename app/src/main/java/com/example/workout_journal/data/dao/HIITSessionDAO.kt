package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.workout_journal.data.entity.HIITSession

@Dao
interface HIITSessionDAO {
    @Insert
    suspend fun insertHIITSession(hiitSession: HIITSession)

    @Delete
    suspend fun deleteHIITSession(hiitSession: HIITSession)


}