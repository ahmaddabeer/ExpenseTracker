package com.example.expensetracker.presentation.screens.history


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.expensetracker.presentation.navigation.Route
import com.example.expensetracker.presentation.screens.home_screen.component.Expense
import com.example.expensetracker.presentation.screens.home_screen.component.NoExpensesYet
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel

private val Green = Color(0xFF16855A)
private val DarkText = Color(0xFF202A24)
private val GreyText = Color(0xFF858585)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCategory(controller: NavController, category: String = "") {

    val viewModel: ExpenceViewModel = hiltViewModel()
    val expenses by viewModel.allExpense.collectAsState()


    val totalExpenses = expenses.filter { it.category == category }
        .sumOf { it.amount ?: 0.0 }

    Scaffold(
        modifier = Modifier.fillMaxSize(),



    ) { innerPadding ->

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
                    text = "$category History",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Track all your $category  transactions",
                    color = GreyText,
                    fontSize = 14.sp
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
                            "Total Spending",
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "₹ %.2f".format(totalExpenses),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            "All expenses",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp
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
                    items = expenses.filter { it.category == category },
                    key = { it.id }
                ) { expenseItem ->

                    Expense(expenseItem,
                        {
                            controller.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("expense",expenseItem)

                            controller.navigate(Route.DetailExpenseCard(expenseItem.id))
                        })

                }
            }
        }
    }
}
