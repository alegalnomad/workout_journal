package com.example.workout_journal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.workout_journal.data.entity.RunSplits

@Dao
interface RunSplitsDAO {
    @Insert
    suspend fun insertRunSplit(runSplit: RunSplits)

}