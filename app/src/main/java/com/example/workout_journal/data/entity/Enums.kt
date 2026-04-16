package com.example.workout_journal.data.entity

enum class WorkoutType { WEIGHTS, RUN, HIIT }

enum class SetType { WORKING, WARMUP, DROP, FAILURE }

enum class RunDistance(val km: Int) {
    ONE_KM(1),
    FIVE_KM(5),
    TEN_KM(10),
    HALF_MARATHON(21),
    FULL_MARATHON(42),
    LONGEST_RUN(0)
}

enum class DistanceUnit { KM, MILE }
enum class WeightUnit { KG, POUNDS}