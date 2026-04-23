package com.example.workout_journal.data.datastore

import com.example.workout_journal.data.entity.MeasureUnit


data class UserPreferences(
    val measureUnit: MeasureUnit = MeasureUnit.METRIC,
    val userName: String = "User",
    val selectedExerciseIds: Set<Int> = emptySet()
)