package com.example.workout_journal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "running_splits",
    foreignKeys = [ForeignKey(
        entity = Running::class,
        parentColumns = ["id"],
        childColumns = ["runningId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RunningSplit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(index = true) val runningId: Int,
    val kilometre: Int,        // e.g. 1, 2, 3...
    val splitTime: Float,      // time to complete that km in seconds
    val avgPace: Float,        // mins per km
    val elevationGain: Float? = null // elevation for that km
)