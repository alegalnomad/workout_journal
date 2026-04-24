package com.example.workout_journal

sealed class Route (val path: String){

    sealed class Workout(path: String) : Route(path) {
        object List : Workout("workout/list")
        data class Detail(val workoutId: Long) : Workout("workout/detail/{workoutId}") {
            fun resolvedPath() = "workout/detail/$workoutId"
            companion object {
                const val PATH = "workout/detail/{workoutId}"
                const val ARG = "workoutId"
            }
        }}

    sealed class Weight(path: String) : Route(path) {
        object Record : Weight("weight/record")
        object Summary : Weight("weight/summary")
    }

    sealed class Run(path: String) : Route(path) {
        object Record : Run("run/record")
        object Summary : Run("run/summary")
    }

    sealed class HIIT(path: String) : Route(path) {
        object Record : HIIT("hiit/record")
        object Timer : HIIT("hiit/timer")
        object Summary : HIIT("hiit/summary")
    }

    object Stats : Route("stats")
    object Settings : Route("settings")
 }
