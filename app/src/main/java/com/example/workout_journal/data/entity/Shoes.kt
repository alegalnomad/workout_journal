package com.example.workout_journal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shoes (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val shoeName : String,
    val shoeDistance : Double = 0.0,
    val isRetired : Boolean = false,
)