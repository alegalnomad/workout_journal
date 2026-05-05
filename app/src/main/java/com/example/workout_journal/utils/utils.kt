package com.example.workout_journal.utils


import com.example.workout_journal.data.entity.MeasureUnit
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatElapsedTime(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    val hours = (millis / (1000 * 60 * 60))

    return if (hours > 0) {
        "%02d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%02d:%02d".format(minutes, seconds)
    }
}

fun toDate(millis: Long): String {
    val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
    return date.format(format)
}

fun MeasureUnit.weightLabel() = if (this == MeasureUnit.IMPERIAL) "lbs" else "kg"
fun MeasureUnit.distanceLabel() = if (this == MeasureUnit.IMPERIAL) "mi" else "km"

fun toMiles(distance: Double): Double = distance * 0.00062137
fun toKm(distance: Double): Double = distance / 1000

fun toTitleCase(word: String): String = word.lowercase().split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { it.uppercase() }
}