package com.example.expensetracker.presentation.screens.history_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.presentation.component.bottom_nav_bar.BottomNavBar
import com.example.expensetracker.presentation.navigation.Route
import com.example.expensetracker.presentation.screens.home_screen.component.Expense
import com.example.expensetracker.presentation.screens.home_screen.component.NoExpensesYet
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel
import kotlin.math.exp

private val Green = Color(0xFF16855A)
private val DarkText = Color(0xFF202A24)
private val GreyText = Color(0xFF858585)

@Composable
fun HistoryScreen(controller: NavController) {

    val viewModel: ExpenceViewModel = hiltViewModel()
    val expenses by viewModel.allExpense.collectAsState()

    val totalSpending = expenses.sumOf { it.amount ?: 0.0 }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {


        }) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp),

            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Text(
                    text = "Expense History",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Track all your transactions", color = GreyText, fontSize = 14.sp
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Green
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            "Total Spending", color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "₹ %.2f".format(totalSpending),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            "All expenses", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp
                        )
                    }
                }
            }

            item {
                Text(
                    "Recent Transactions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )
            }

            if (expenses.isEmpty()) {

                item {
                    NoExpensesYet()
                }

            } else {

                items(
                    items = expenses, key = { it.id }) { expenseItem ->

                    Expense(
                        expenseItem, {

                            controller.navigate(Route.DetailExpenseCard(expenseItem.id))
                        })
                }
            }
        }
    }
}
