package com.example.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.presentation.screens.add_expense_screen.AddExpenseScreen
import com.example.expensetracker.presentation.screens.detailExpense.DetailExpenseCard
import com.example.expensetracker.presentation.screens.editexpenses.EditExpenses
import com.example.expensetracker.presentation.screens.history.DetailCategory
import com.example.expensetracker.presentation.screens.history_screen.HistoryScreen
import com.example.expensetracker.presentation.screens.home_screen.HomeScreen
import com.example.expensetracker.presentation.screens.profile_screen.Profile
import com.example.expensetracker.presentation.screens.profile_screen.component.ProfileTopBar
import com.example.expensetracker.presentation.screens.settings_screen.SettingScreen
import com.example.expensetracker.presentation.screens.statistics_screen.StatisticsScreen

@Composable
fun NavigationGraph() {


    val controller = rememberNavController()



    NavHost(
        navController = controller,
        startDestination = Route.HomeScreen,

        ) {
        composable<Route.HomeScreen> {
            HomeScreen(controller)


        }

        composable<Route.HistoryScreen> {
            HistoryScreen(controller)
        }

        composable<Route.SettingScreen> {
            SettingScreen(controller)
        }

        composable<Route.AddExpenseScreen> {
            AddExpenseScreen(controller)
        }

        composable<Route.StatisticsScreen> {
            StatisticsScreen(controller)
        }



        composable<Route.Profile> { Profile(controller) }

        composable<Route.DetailCategory> { backStackEntry ->

            val route = backStackEntry.toRoute<Route.DetailCategory>()

            DetailCategory(
                controller = controller,
                category = route.category
            )
        }

        composable<Route.DetailExpenseCard>{ backStackEntry->


            val route = backStackEntry.toRoute<Route.DetailExpenseCard>()


            DetailExpenseCard(controller= controller,route.expenseId)
        }


        composable<Route.EditExpenses>{backStack->

            val route = backStack.toRoute<Route.EditExpenses>()

            EditExpenses(controller,route.expenseId)
        }


    }
}

