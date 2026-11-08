package com.example.expensetracker.presentation.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.presentation.component.bottom_nav_bar.BottomNavBar
import com.example.expensetracker.presentation.navigation.Route
import com.example.expensetracker.presentation.screens.home_screen.component.CategoriesCard
import com.example.expensetracker.presentation.screens.home_screen.component.Expense
import com.example.expensetracker.presentation.screens.home_screen.component.HomeExpenseCard
import com.example.expensetracker.presentation.screens.home_screen.component.HomeScreenTopBar
import com.example.expensetracker.presentation.screens.home_screen.component.NoExpensesYet
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Car
import dev.seyfarth.tablericons.outlined.Invoice
import dev.seyfarth.tablericons.outlined.ShoppingBag
import dev.seyfarth.tablericons.outlined.ToolsKitchen

@Composable
fun HomeScreen(
    controller: NavHostController
) {

    val viewModel: ExpenceViewModel = hiltViewModel()
    val expense by viewModel.allExpense.collectAsState()

    val totalSpending = expense.sumOf { it.amount ?: 0.0 }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            HomeScreenTopBar(controller)
        },

        bottomBar = {
            BottomNavBar(controller)
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F9F7))
                .padding(innerPadding),

            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 10.dp
            )
        ) {

            item {
                HomeExpenseCard(totalSpending.toString())

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        controller.navigate(Route.AddExpenseScreen)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF16855A),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Text(
                        text = "+ Add Expense",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Categories",
                    color = Color(0xFF222222),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoriesCard(
                        TablerIcons.Outlined.ToolsKitchen,
                        "Food"
                    ) { category ->
                        controller.navigate(Route.DetailCategory(category))
                    }

                    CategoriesCard(
                        TablerIcons.Outlined.Car,
                        "Travel"
                    ) { category ->
                        controller.navigate(Route.DetailCategory(category))
                    }

                    CategoriesCard(
                        TablerIcons.Outlined.ShoppingBag,
                        "Shopping"
                    ) { category ->
                        controller.navigate(Route.DetailCategory(category))
                    }

                    CategoriesCard(
                        TablerIcons.Outlined.Invoice,
                        "Bills"
                    ) { category ->
                        controller.navigate(Route.DetailCategory(category))
                    }


                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recent Expenses",
                        color = Color(0xFF222222),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "See All",
                        color = Color(0xFF16855A),
                        fontWeight = FontWeight.Medium,

                        modifier = Modifier.clickable {

                            controller.navigate(Route.HistoryScreen) {
                                popUpTo<Route.HomeScreen> {

                                    inclusive = false
                                }
                                launchSingleTop = true
                            }

                        }
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
            }

            if (expense.isEmpty()) {

                item {
                    NoExpensesYet()
                    Spacer(modifier = Modifier.height(15.dp))
                }

            } else {

                items(
                    items = expense.take(3),
                    key = { it.id }
                ) { expenseItem ->


                    Expense(expenseItem,
                        {
                            controller.navigate(Route.DetailExpenseCard(expenseId = expenseItem.id))})


                }
            }
        }
    }
}
