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
            childColumns = ["run_id"],
            onDelete = ForeignKey.CASCADE
        )
],
    indices = [Index("run_id")]

)
data class RunSplits(
    @PrimaryKey(autoGenerate = true) val id : Int =0,
    val run_id : Int,
    val distance : Float,
    val split_time : Long,
    val elevation_gain : Float,

    )