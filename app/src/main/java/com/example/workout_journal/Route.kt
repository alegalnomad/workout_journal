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
        object Record : Weight("run/record")
        object Summary : Weight("run/summary")
    }

    sealed class HIIT(path: String) : Route(path) {
        object Record : Weight("hiit/record")
        object Timer : Weight("hiit/summary")
        object Summary : Weight("hiit/summary")
    }

    object Stats : Route("stats")
    object Settings : Route("settings")
 }
