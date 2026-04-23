package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hiitExercise",
    foreignKeys = [
        ForeignKey(
            entity = HIITSession::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        ),
            ForeignKey(
                entity = HIITExerciseName::class,
                parentColumns = ["id"],
                childColumns = ["exerciseNameId"],
                onDelete = ForeignKey.RESTRICT
            )
            ],
indices = [Index("sessionId"), Index("exerciseNameId")]
    )
data class HIITExercise(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val exerciseNameId: Int,
    val notes: String,
    val order: Int
)