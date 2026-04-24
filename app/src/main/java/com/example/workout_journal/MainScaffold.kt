package com.example.workout_journal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workout_journal.ui.nav.AppNavGraph
import com.example.workout_journal.ui.nav.BottomNavBar
import com.example.workout_journal.ui.theme.Workout_journalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentGraphRoute = navBackStackEntry?.destination?.parent?.route
    println(currentGraphRoute)
    val showBottomBar = currentGraphRoute  in listOf(
        "home_graph",
        "stats_graph",
        "settings_graph"
    )

    Scaffold(
        topBar = {
            TopAppBar(
            title = { Text(text = "Workout Journal") }
            )
                 },
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavGraph(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffoldContent(
    currentGraphRoute: String?,
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val showBottomBar = currentGraphRoute in listOf("home_graph", "stats", "settings")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Workout Journal") })
        },
        bottomBar = {
            if (showBottomBar) BottomNavBar(navController)
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    val navController = rememberNavController()
    Workout_journalTheme {
        MainScaffoldContent(
            currentGraphRoute = "home_graph",
            navController = navController
        ) { innerPadding ->
            // placeholder — no NavGraph, no Hilt
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize())
        }
    }
}