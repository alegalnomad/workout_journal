package com.example.workout_journal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workout_journal.data.dao.*
import com.example.workout_journal.data.entity.*

@Database(
    entities = [
        Workout::class,
        Run::class,
        RunPB::class,
        RunSplits::class,
        WeightExercise::class,
        WeightExerciseName::class,
        WeightSet::class,
        WeightPB::class,
        HIITSession::class,
        HIITExercise::class,
        HIITExerciseName::class,
        BodyHeightWeight::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDAO
    abstract fun runDao(): RunDAO
    abstract fun runPbDao(): RunPBDAO
    abstract fun runSplitsDao(): RunSplitsDAO
    abstract fun weightExerciseDao(): WeightExerciseDAO
    abstract fun weightExerciseNameDao(): WeightExerciseNameDAO
    abstract fun weightSetDao(): WeightSetDAO
    abstract fun weightPbDao(): WeightPBDAO
    abstract fun hiitSessionDao(): HIITSessionDAO
    abstract fun hiitExerciseDao(): HIITExerciseDAO
    abstract fun hiitExerciseNameDao(): HIITExerciseNameDAO
    abstract fun bodyHeightWeightDao(): BodyHeightWeightDAO
}
