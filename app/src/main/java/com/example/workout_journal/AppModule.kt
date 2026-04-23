package com.example.workout_journal

import android.content.Context
import androidx.room.Room
import com.example.workout_journal.data.AppDatabase
import com.example.workout_journal.data.dao.*
import com.example.workout_journal.data.datastore.UserPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserPreferencesManager(
        @ApplicationContext context: Context
    ): UserPreferencesManager {
        return UserPreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "workout_journal_db"
        ).build()
    }

    @Provides
    fun provideWorkoutDao(db: AppDatabase): WorkoutDAO = db.workoutDao()

    @Provides
    fun provideRunDao(db: AppDatabase): RunDAO = db.runDao()

    @Provides
    fun provideRunPbDao(db: AppDatabase): RunPBDAO = db.runPbDao()

    @Provides
    fun provideRunSplitsDao(db: AppDatabase): RunSplitsDAO = db.runSplitsDao()

    @Provides
    fun provideWeightExerciseDao(db: AppDatabase): WeightExerciseDAO = db.weightExerciseDao()

    @Provides
    fun provideWeightExerciseNameDao(db: AppDatabase): WeightExerciseNameDAO = db.weightExerciseNameDao()

    @Provides
    fun provideWeightSetDao(db: AppDatabase): WeightSetDAO = db.weightSetDao()

    @Provides
    fun provideWeightPbDao(db: AppDatabase): WeightPBDAO = db.weightPbDao()

    @Provides
    fun provideHiitSessionDao(db: AppDatabase): HIITSessionDAO = db.hiitSessionDao()

    @Provides
    fun provideHiitExerciseDao(db: AppDatabase): HIITExerciseDAO = db.hiitExerciseDao()

    @Provides
    fun provideHiitExerciseNameDao(db: AppDatabase): HIITExerciseNameDAO = db.hiitExerciseNameDao()

    @Provides
    fun provideBodyHeightWeightDao(db: AppDatabase): BodyHeightWeightDAO = db.bodyHeightWeightDao()
}
