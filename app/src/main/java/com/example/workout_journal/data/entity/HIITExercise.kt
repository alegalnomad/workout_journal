package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiitExercise",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        ),
            ForeignKey(
                entity = HIITExerciseName::class,
                parentColumns = ["id"],
                childColumns = ["exerciseNameId"],
                onDelete = ForeignKey.CASCADE
            )
            ],
indices = [Index("workoutId"), Index("exerciseNameId")]
    )
data class HIITExercise(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workoutId: Long,
    val exerciseNameId: Int,
    val notes: String,
    )