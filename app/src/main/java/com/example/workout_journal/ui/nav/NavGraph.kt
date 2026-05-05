package com.example.workout_journal.ui.nav


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.workout_journal.Route
import com.example.workout_journal.ui.screens.ExerciseSelectionScreen
import com.example.workout_journal.ui.screens.HIITRecordScreen
import com.example.workout_journal.ui.screens.HIITSummaryScreen
import com.example.workout_journal.ui.screens.HIITTimerScreen
import com.example.workout_journal.ui.screens.WorkoutDetailScreen
import com.example.workout_journal.ui.screens.HomeScreen
import com.example.workout_journal.ui.screens.RunRecordScreen
import com.example.workout_journal.ui.screens.RunSummaryScreen
import com.example.workout_journal.ui.screens.SettingsScreen
import com.example.workout_journal.ui.screens.StatsScreen
import com.example.workout_journal.ui.screens.WeightRecordScreen
import com.example.workout_journal.ui.screens.WeightSummaryScreen
import com.example.workout_journal.ui.viewmodel.HIITViewModel
import com.example.workout_journal.ui.viewmodel.HomeViewModel
import com.example.workout_journal.ui.viewmodel.RunViewModel
import com.example.workout_journal.ui.viewmodel.SettingsViewModel
import com.example.workout_journal.ui.viewmodel.WeightsViewModel


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController())
{
    NavHost(
        navController = navController,
        startDestination = "home_graph",
        modifier = modifier
    ) {
        // Home graph
        navigation(
            route = "home_graph",
            startDestination = Route.Workout.List.path
        ){
            composable(Route.Workout.List.path) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("home_graph")
                }
                val viewModel:HomeViewModel = hiltViewModel(viewModelStoreOwner = parentEntry, key = null)
                HomeScreen(
                    viewModel = viewModel,
                    onWorkoutSelect = { workoutId, workoutType ->
                        viewModel.selectWorkout(workoutId, workoutType)
                        navController.navigate(Route.Workout.Detail(workoutId).resolvedPath())
                    },
                    onExerciseSelection = {route ->
                        navController.navigate(route)
                    }
                )

            }

            composable(
                route = Route.Workout.Detail.PATH,
                arguments = listOf(navArgument(Route.Workout.Detail.ARG) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("home_graph")
                }
                val viewModel: HomeViewModel = hiltViewModel(parentEntry, null)

                WorkoutDetailScreen(
                    viewModel = viewModel
                )
            }


        }
        // Weight graph
        navigation(
            route = "weight",
            startDestination = Route.Weight.Record.path
        ) {
            composable(Route.Weight.Record.path) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("weight")
                }
                val viewModel: WeightsViewModel = hiltViewModel(parentEntry)
                WeightRecordScreen(
                    viewModel =viewModel,
                    onFinish = {
                        navController.navigate(Route.Weight.Summary.path)
                    }
                )
            }

            composable(Route.Weight.Summary.path) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("weight")
                }
                val viewModel: WeightsViewModel = hiltViewModel(parentEntry)
                WeightSummaryScreen(
                    viewModel = viewModel,
                    onSave = {
                        viewModel.onSave()
                        navController.popBackStack(
                            route = Route.Workout.List.path,
                            inclusive = false
                        )
                    }
                )
            }
        }
        // Run graph
        navigation(
            route = "run",
            startDestination = Route.Run.Record.path
        ) {
            composable (Route.Run.Record.path) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("run")
                }
                val viewModel: RunViewModel = hiltViewModel(parentEntry)
                RunRecordScreen(
                    viewModel = viewModel,
                    onFinish = {
                        navController.navigate(Route.Run.Summary.path)
                    })
            }

            composable(Route.Run.Summary.path) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("run")
                }
                val viewModel: RunViewModel = hiltViewModel(parentEntry)
                RunSummaryScreen(
                    viewModel = viewModel,
                    onSave = { title:String?, note:String? ->
                        viewModel.saveRun(title,note)
                        navController.popBackStack(
                            route = Route.Workout.List.path,
                            inclusive = false
                        )
                    }
                )
            }

        }
        //HIIT graph
        navigation(
            route = "hiit",
            startDestination = Route.HIIT.Record.path
        ) {
            composable (Route.HIIT.Record.path) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("hiit")
                }
                val viewModel: HIITViewModel = hiltViewModel(parentEntry,null)
                HIITRecordScreen(
                    viewModel = viewModel,
                    onStart = {
                        navController.navigate(Route.HIIT.Timer.path)
                    }
                )
            }

            composable(Route.HIIT.Timer.path) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("hiit")
                }
                val viewModel: HIITViewModel = hiltViewModel(parentEntry,null)
                HIITTimerScreen(
                    viewModel = viewModel,
                    onFinish = {
                        navController.navigate(Route.HIIT.Summary.path)
                    }
                )
            }

            composable(Route.HIIT.Summary.path) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("hiit")
                }
                val viewModel: HIITViewModel = hiltViewModel(parentEntry, null)
                HIITSummaryScreen(
                    viewModel = viewModel,
                    onSave = { note:String? ->
                        viewModel.saveWorkout(note)
                        navController.popBackStack(
                            route = Route.Workout.List.path,
                            inclusive = false
                        )
                    }
                )
            }
        }
        // Stats graph
        navigation(
            route = "stats_graph",
            startDestination = Route.Stats.path
        ) {
            composable(Route.Stats.path) {
                StatsScreen()
            }
        }
        // Settings graph
        navigation(
            route = "settings_graph",
            startDestination = Route.Settings.Home.path
        ) {
            composable(Route.Settings.Home.path) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("settings_graph")
                }
                val viewModel: SettingsViewModel = hiltViewModel(parentEntry,null)
                SettingsScreen(
                    viewModel = viewModel,
                    onSelectExercise = {
                        navController.navigate(Route.Settings.ExerciseSelection.path)
                    }
                )
            }
        }

        composable (Route.Settings.ExerciseSelection.path){
            val parentEntry = remember(it) {
                navController.getBackStackEntry("settings_graph")
            }
            val viewModel: SettingsViewModel = hiltViewModel(parentEntry,null)
            ExerciseSelectionScreen(
                viewModel = viewModel,
                onSave = { ids: Set<Int> ->
                    viewModel.saveSelectedExerciseIds(ids)
                    navController.popBackStack()
                }
            )
        }
    }
}