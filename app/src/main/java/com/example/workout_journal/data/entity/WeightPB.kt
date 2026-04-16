package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weight_pb",
    foreignKeys = [
        ForeignKey(
            entity = WeightExerciseName::class,
            parentColumns = ["id"],
            childColumns = ["exerciseNameId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WeightExercise::class,
            parentColumns = ["id"],
            childColumns = ["weightExerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("exerciseNameId"), Index(value=["exerciseNameId","bestWeightKg"]), Index("weightExerciseId")]

)
data class WeightPB(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseNameId : Int,
    val weightExerciseId : Long,
    val bestWeightKg : Float,
    val dateAchieved : Long = System.currentTimeMillis()
)

