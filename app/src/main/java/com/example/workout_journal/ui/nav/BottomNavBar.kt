package com.example.workout_journal.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState



sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home    : BottomNavItem("home_graph",    "Home",    Icons.Default.Home)
    object Stats    : BottomNavItem("stats_graph",    "Stats",    Icons.Default.BarChart)
    object Settings : BottomNavItem("settings_graph", "Settings", Icons.Default.Settings)
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val tabs = listOf(
        BottomNavItem.Home,
        BottomNavItem.Stats,
        BottomNavItem.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentGraphRoute = navBackStackEntry?.destination?.parent?.route

    NavigationBar() {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentGraphRoute == tab.route,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) }
            )

        }
    }

}