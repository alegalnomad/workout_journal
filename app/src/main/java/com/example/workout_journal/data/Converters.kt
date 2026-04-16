package com.example.workout_journal.data

import androidx.room.TypeConverter
import com.example.workout_journal.data.entity.DistanceUnit
import com.example.workout_journal.data.entity.RunDistance
import com.example.workout_journal.data.entity.SetType
import com.example.workout_journal.data.entity.WeightUnit
import com.example.workout_journal.data.entity.WorkoutType

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
    fun fromWeightUnit(value: WeightUnit): String = value.name

    @TypeConverter
    fun toWeightUnit(value: String): WeightUnit = WeightUnit.valueOf(value)

    @TypeConverter
    fun fromDistanceUnit(value: DistanceUnit): String = value.name

    @TypeConverter
    fun toDistanceUnit(value: String): DistanceUnit = DistanceUnit.valueOf(value)
}