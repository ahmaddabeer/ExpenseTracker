package com.example.expensetracker.presentation.screens.statistics_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.presentation.component.bottom_nav_bar.BottomNavBar
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel

private val Green = Color(0xFF16855A)
private val DarkText = Color(0xFF202A24)
private val GreyText = Color(0xFF858585)

@Composable
fun StatisticsScreen(controller: NavHostController) {

    var selectedFilter by remember {
        mutableStateOf("Month")
    }

    val filters = listOf("Week", "Month", "Year")

    val viewModel: ExpenceViewModel = hiltViewModel()
    val expense by viewModel.allExpense.collectAsState()

    val totalSpending = expense.sumOf { it.amount ?: 0.0 }

    val foodTotal = expense
        .filter { it.category == "Food" }
        .sumOf { it.amount ?: 0.0 }

    val travelTotal = expense
        .filter { it.category == "Travel" }
        .sumOf { it.amount ?: 0.0 }


    val shoppingTotal = expense
        .filter { it.category == "Shopping" }
        .sumOf { it.amount ?: 0.0 }

    val billsTotal = expense
        .filter { it.category == "Bills" }
        .sumOf { it.amount ?: 0.0 }


    val foodProfgess = if (totalSpending > 0) {

        (foodTotal / totalSpending).toFloat()

    } else 0f

    val travelProgress = if (totalSpending > 0) {

        (travelTotal / totalSpending).toFloat()

    } else 0f


    val shoppingProgress = if (totalSpending > 0) {

        (shoppingTotal / totalSpending).toFloat()
    } else 0f


    val billProgress = if (totalSpending > 0) {

        (billsTotal / totalSpending).toFloat()

    } else 0f

    Scaffold(
        modifier = Modifier.fillMaxSize(),


        bottomBar = {

            BottomNavBar(controller)
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {

            item {


                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Statistics",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Analyze your spending habits",
                    fontSize = 14.sp,
                    color = GreyText
                )

                Spacer(Modifier.height(22.dp))

                // Time Filter
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    filters.forEachIndexed { index, filter ->

                        SegmentedButton(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = filters.size
                            )
                        ) {
                            Text(filter)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Total Spending Card
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
                            totalSpending.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "12% less than previous month",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(25.dp))

                // Chart Heading
                Text(
                    "Spending Overview",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(Modifier.height(12.dp))

                SpendingChart()

                Spacer(Modifier.height(25.dp))

                // Category Heading
                Text(
                    "Category Breakdown",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(Modifier.height(15.dp))

                CategoryStatistic("Food", "₹${foodTotal}", foodProfgess)
                CategoryStatistic("Travel", "₹${travelTotal}", travelProgress)
                CategoryStatistic("Shopping", "₹${shoppingTotal}",  shoppingProgress)
                CategoryStatistic("Bills", "₹${billsTotal}", billProgress)

                Spacer(Modifier.height(20.dp))
            }
        }
    }

}


@Composable
private fun SpendingChart() {

    val values = listOf(
        0.4f, 0.7f, 0.5f, 0.9f, 0.6f, 0.8f, 0.5f
    )

    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(
                Color(0xFFF8FAF8),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEachIndexed { index, value ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Box(
                    modifier = Modifier
                        .width(22.dp)
                        .height((110 * value).dp)
                        .background(
                            if (index == 3) Green else Color(0xFFB7DEC9),
                            RoundedCornerShape(
                                topStart = 6.dp,
                                topEnd = 6.dp
                            )
                        )
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    days[index],
                    fontSize = 10.sp,
                    color = GreyText
                )
            }
        }
    }
}

@Composable
private fun CategoryStatistic(
    title: String,
    amount: String,
    progress: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                fontWeight = FontWeight.Medium,
                color = DarkText
            )

            Text(
                amount,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )
        }

        Spacer(Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp),
            color = Green,
            trackColor = Color(0xFFE8F5EE)
        )
    }
}
