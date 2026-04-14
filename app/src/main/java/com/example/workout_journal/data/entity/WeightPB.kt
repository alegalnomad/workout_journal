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
            childColumns = ["exercise_name_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WeightExercise::class,
            parentColumns = ["id"],
            childColumns = ["weight_exercise_id"]
        )
    ],
    indices = [Index("exercise_name_id"), Index(value=["exercise_name_id","best_weight_kg"]), Index("weight_exercise_id")]

)
data class WeightPB(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exercise_name_id : Int,
    val weight_exercise_id : Int,
    val best_weight_kg : Float,
    val date_achieved : Long = System.currentTimeMillis()
)

