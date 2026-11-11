package com.example.expensetracker.presentation.navigation

import androidx.navigation.NavController
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel
import kotlinx.serialization.Serializable


@Serializable
sealed interface Route {

    @Serializable
    object HomeScreen : Route


    @Serializable
    object AddExpenseScreen : Route


    @Serializable
    object HistoryScreen : Route

    @Serializable
    data class DetailExpenseCard(
       val expenseId: Int
    )



    @Serializable
    data class EditExpenses(
        val expenseId:Int
    )
    @Serializable
    data class DetailCategory(
        val category: String,


    )






@Serializable
object StatisticsScreen : Route


@Serializable
object Profile : Route

@Serializable
object SettingScreen : Route
}

