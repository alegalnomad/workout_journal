package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weight_set",
    foreignKeys = [
        ForeignKey(
            entity = WeightExercise::class,
            parentColumns = ["id"],
            childColumns = ["weight_exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("weight_exercise_id")]
)
data class WeightSet(
    @PrimaryKey(autoGenerate = true) val id : Int =0,
    val weight_exercise_id: Int,
    val set : Int,
    val reps : Int,
    val weight_kg : Float,
    val setType: SetType
    )