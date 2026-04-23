package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weightSet",
    foreignKeys = [
        ForeignKey(
            entity = WeightExercise::class,
            parentColumns = ["id"],
            childColumns = ["weightExerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("weightExerciseId")]
)
data class WeightSet(
    @PrimaryKey(autoGenerate = true) val id : Long =0,
    val weightExerciseId: Long,
    val set : Int,
    val reps : Int,
    val weightKg : Double,
    val setType: SetType
    )