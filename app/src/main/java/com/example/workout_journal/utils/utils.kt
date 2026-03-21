package com.example.workout_journal.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun convertWeight(value: Float, unit: String): Float {
    return if (unit == "lbs") value * 2.20462f else value
}

fun convertDistance(value: Float, unit: String): Float {
    return if (unit == "mi") value * 0.621371f else value
}