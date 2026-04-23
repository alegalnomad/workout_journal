package com.example.workout_journal.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.workout_journal.data.entity.MeasureUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")
class UserPreferencesManager(private val context: Context) {
    private object PreferencesKeys {
        val MEASURE_UNIT = stringPreferencesKey("measure_unit")
        val USER_NAME = stringPreferencesKey("user_name")
        val SELECTED_EXERCISE_IDS = stringPreferencesKey("selected_exercise_ids")
}

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .map { preferences ->
            // Get the string from storage, default to METRIC.name
            val measureUnitName = preferences[PreferencesKeys.MEASURE_UNIT] ?: MeasureUnit.METRIC.name

            UserPreferences(
                // Convert the String back into an Enum safely
                measureUnit = try {
                    MeasureUnit.valueOf(measureUnitName)
                } catch (e: Exception) {
                    MeasureUnit.METRIC
                },
                userName = preferences[PreferencesKeys.USER_NAME] ?: "User",
                selectedExerciseIds = preferences[PreferencesKeys.SELECTED_EXERCISE_IDS]
                    ?.split(",")
                    ?.mapNotNull { it.toIntOrNull() }
                    ?.toSet() ?: emptySet()
            )
        }

    // Write functions now take the Enum as an argument
    suspend fun updateMeasureUnit(unit: MeasureUnit) {
        context.dataStore.edit { it[PreferencesKeys.MEASURE_UNIT] = unit.name }
    }

    suspend fun updateUserName(name: String) {
        context.dataStore.edit { it[PreferencesKeys.USER_NAME] = name }
    }

    suspend fun saveSelectedExerciseIds(ids: Set<Int>) {
        context.dataStore.edit { it[PreferencesKeys.SELECTED_EXERCISE_IDS] = ids.joinToString(",") }
    }

}