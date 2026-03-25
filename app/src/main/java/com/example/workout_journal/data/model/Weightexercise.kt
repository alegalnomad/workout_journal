package com.example.workout_journal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "weight_exercises",
    foreignKeys = [ForeignKey(
    entity = Workout::class,
    parentColumns = ["id"],
    childColumns = ["workoutId"],
    onDelete = ForeignKey.CASCADE
)]
)
data class WeightExercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val workoutId: Int,
    val name: String
)