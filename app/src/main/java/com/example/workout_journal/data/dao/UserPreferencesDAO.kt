package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.workout_journal.data.entity.UserPreferences

@Dao
interface UserPreferencesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPref(userPref: UserPreferences)
}