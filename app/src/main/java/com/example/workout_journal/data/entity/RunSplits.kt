package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "run_splits",
    foreignKeys = [
        ForeignKey(
            entity = Run::class,
            parentColumns = ["id"],
            childColumns = ["runId"],
            onDelete = ForeignKey.CASCADE
        )
],
    indices = [Index("runId")]

)
data class RunSplits(
    @PrimaryKey(autoGenerate = true) val id : Long =0,
    val runId : Long,
    val distance : Int,
    val splitTime : Long,
    val elevationGain : Float,

    )