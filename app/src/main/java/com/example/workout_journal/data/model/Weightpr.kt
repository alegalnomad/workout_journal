package com.example.workout_journal.data.model

import androidx.room.*

@Entity(
    tableName = "weight_prs",
    foreignKeys = [ForeignKey(
        entity = WeightExercise::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeightPr(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val exerciseId: Int,
    val weight: Float,
    val reps: Int,
    val dateAchieved: Long = System.currentTimeMillis()
)