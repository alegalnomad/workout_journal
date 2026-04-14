package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiit_exercise",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE
        ),
            ForeignKey(
                entity = HIITExerciseName::class,
                parentColumns = ["id"],
                childColumns = ["exercise_name_id"],
                onDelete = ForeignKey.CASCADE
            )
            ],
indices = [Index("workout_id"), Index("exercise_name_id")]
    )
data class HIITExercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val workout_id: Int,
    val exercise_name_id: Int,
    val notes: String,
    )