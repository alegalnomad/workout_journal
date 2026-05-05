package com.example.workout_journal.data.entity

enum class WorkoutType { WEIGHTS, RUN, HIIT }

enum class SetType { WORKING, WARMUP, DROP, FAILURE }

enum class RunDistance(val km: Int, val label:String) {
    ONE_KM(1_000, "1 Km"),
    FIVE_KM(5_000,"5 Km"),
    TEN_KM(10_000, "10 Km"),
    HALF_MARATHON(21_097, "Half Marathon"),
    FULL_MARATHON(42_195, "Marathon"),
    LONGEST_RUN(0, "Longest Run")
}

enum class MeasureUnit { METRIC, IMPERIAL }
