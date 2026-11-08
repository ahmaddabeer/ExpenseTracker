package com.example.expensetracker.presentation.component.bottom_nav_bar

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expensetracker.presentation.navigation.Route
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.ChartBar
import dev.seyfarth.tablericons.outlined.History
import dev.seyfarth.tablericons.outlined.Home
import dev.seyfarth.tablericons.outlined.Settings

@Composable
fun BottomNavBar(controller: NavController) {

    val navItems = listOf(
        NavigationItem("Home", TablerIcons.Outlined.Home, Route.HomeScreen),
        NavigationItem("Statistic", TablerIcons.Outlined.ChartBar, Route.StatisticsScreen),
        NavigationItem("History", TablerIcons.Outlined.History, Route.HistoryScreen),
        NavigationItem("Setting", TablerIcons.Outlined.Settings, Route.SettingScreen)
    )

    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        navItems.forEach { item ->
            // Safely verify current route with modern Navigation 2.8+ APIs
            val isSelected = currentDestination?.hasRoute(item.route::class) == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    controller.navigate(item.route) {
                        popUpTo(controller.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Image(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: Route
)
