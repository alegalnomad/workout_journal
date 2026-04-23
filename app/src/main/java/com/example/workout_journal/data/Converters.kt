package com.example.workout_journal.data

import androidx.room.TypeConverter
import com.example.workout_journal.data.entity.RunDistance
import com.example.workout_journal.data.entity.SetType
import com.example.workout_journal.data.entity.WorkoutType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil

class Converters {
    @TypeConverter
    fun fromWorkoutType(value: WorkoutType): String = value.name

    @TypeConverter
    fun toWorkoutType(value: String): WorkoutType = WorkoutType.valueOf(value)

    @TypeConverter
    fun fromSetType(value: SetType): String = value.name

    @TypeConverter
    fun toSetType(value: String): SetType = SetType.valueOf(value)

    @TypeConverter
    fun fromRunDistance(value: RunDistance): String = value.name

    @TypeConverter
    fun toRunDistance(value: String): RunDistance = RunDistance.valueOf(value)

    @TypeConverter
    fun fromLatLangList(path: List<LatLng>?): String? {
        if (path == null) return null
        return PolyUtil.encode(path)

    }

    @TypeConverter
    fun toLatLangList(path: String?): List<LatLng>? {
        if (path == null) return null
        return PolyUtil.decode(path)

    }

}