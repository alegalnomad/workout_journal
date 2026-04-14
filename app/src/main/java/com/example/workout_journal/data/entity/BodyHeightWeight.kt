package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "body_height_weight")
data class BodyHeightWeight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bodyHeight: Float,
    val bodyWeight: Float,
    val dateCreated: Long = System.currentTimeMillis(),
)