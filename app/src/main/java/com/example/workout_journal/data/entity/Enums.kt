package com.example.workout_journal.data.entity

enum class WorkoutType { WEIGHTS, RUN, HIIT }

enum class SetType { WORKING, WARMUP, DROP, FAILURE }

enum class RunDistance(val km: Int) {
    ONE_KM(1_000),
    FIVE_KM(5_000),
    TEN_KM(10_000),
    HALF_MARATHON(21_097),
    FULL_MARATHON(42_195),
    LONGEST_RUN(0)
}

enum class MeasureUnit { METRIC, IMPERIAL }
