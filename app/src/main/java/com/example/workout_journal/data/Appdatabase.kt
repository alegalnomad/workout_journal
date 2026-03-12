package com.example.workout_journal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workout_journal.data.dao.HiitExerciseDao
import com.example.workout_journal.data.dao.HiitSessionDao
import com.example.workout_journal.data.dao.RunningDao
import com.example.workout_journal.data.dao.RunningSplitDao
import com.example.workout_journal.data.dao.WeightExerciseDao
import com.example.workout_journal.data.dao.WeightSetDao
import com.example.workout_journal.data.dao.WorkoutDao
import com.example.workout_journal.data.model.HiitExercise
import com.example.workout_journal.data.model.HiitSession
import com.example.workout_journal.data.model.Running
import com.example.workout_journal.data.model.RunningSplit
import com.example.workout_journal.data.model.WeightExercise
import com.example.workout_journal.data.model.WeightSet
import com.example.workout_journal.data.model.Workout

@Database(
    entities = [
        Workout::class,
        Running::class,
        RunningSplit::class,
        HiitSession::class,
        HiitExercise::class,
        WeightSet::class,
        WeightExercise::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun runningDao(): RunningDao
    abstract fun runningSplitDao(): RunningSplitDao
    abstract fun hiitSessionDao(): HiitSessionDao
    abstract fun hiitExerciseDao(): HiitExerciseDao
    abstract fun weightSetDao(): WeightSetDao
    abstract fun weightExerciseDao(): WeightExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "workout_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}