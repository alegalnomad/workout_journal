package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "run",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE
        )
            ],
    indices = [Index("workout_id")]
)
data class Run(
    @PrimaryKey(autoGenerate = true) val id : Int =0,
    val workout_id : Int,
    val title : String,
    val distance : Float,
    val time_elapsed : Long,
    val active_time: Long,
    val elevation_gain : Float,
    val notes : String,
    val img_path: String,
)