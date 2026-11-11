
package com.example.expensetracker.presentation.screens.detailExpense

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.presentation.navigation.Route
import com.example.expensetracker.presentation.screens.detailExpense.component.TopBarDetailExpense
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Edit
import dev.seyfarth.tablericons.outlined.ToolsKitchen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailExpenseCard(
    controller: NavController,
    expId: Int
) {
    val viewModel: ExpenceViewModel = hiltViewModel()

    val expenseList by viewModel.allExpense.collectAsState()
    val expense = expenseList.find { it.id == expId }

    val context = LocalContext.current

    LaunchedEffect(expense?.id) {
        expense?.let {
            viewModel.onAmountChange(it.amount.toString())
            viewModel.onTitleChange(it.title)
            viewModel.onCategoryChange(it.category)
            viewModel.onPaymentChange(it.paymentMethod)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarDetailExpense(
                controller,
                { controller.popBackStack() },
                {}
            )
        }
    ) { innerPadding ->

        if (expense == null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Expense not found",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Button(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Text("Go Back")
                    }
                }
            }

        } else {

            val date = SimpleDateFormat(
                "dd MMM yyyy hh:mm a",
                Locale.getDefault()
            ).format(Date(expense.createdAt))

            val month = SimpleDateFormat(
                "MMM",
                Locale.getDefault()
            ).format(Date(expense.createdAt))



            val updatedAt = SimpleDateFormat(
                "dd MMM yyyy hh:mm a",
                Locale.getDefault()
            ).format(Date(expense.updatedAt))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                item {

                    // Category Icon and Amount

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 20.dp,
                                bottom = 12.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        color = Color.White.copy(alpha = 0.5f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    imageVector = TablerIcons.Outlined.ToolsKitchen,
                                    contentDescription = "Expense Detail Icon"
                                )
                            }

                            Text(
                                text = "₹ ${expense.amount}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = expense.category,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black,
                                fontWeight = FontWeight.Light
                            )

                            Box(
                                modifier = Modifier
                                    .height(28.dp)
                                    .background(
                                        color = Color(0xFFD8F2E2),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Completed",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF16845A)
                                )
                            }
                        }
                    }

                    // Expense Details

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {

                        DetailRow("Title", expense.title)

                        DetailRow("Category", expense.category)

                        DetailRow("Payment Method", expense.paymentMethod)

                        DetailRow("Date", date)

                        DetailRow("Last Update", if(date == updatedAt) "Not Updated" else updatedAt)


                        DetailRow("Month", month)
                    }

                    // Note

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Note",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = expense.note.ifBlank {
                                "No note added"
                            },
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    // Action Buttons

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 12.dp,
                                bottom = 24.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Button(
                            onClick = {
                                controller.navigate(Route.EditExpenses(expId)) {
                                    popUpTo<Route.DetailExpenseCard> {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(55.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF16845A),
                                contentColor = Color.White
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = TablerIcons.Outlined.Edit,
                                    contentDescription = "Edit"
                                )

                                Text(
                                    text = "Edit Expense",
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.deleteExpense(expense)

                                Toast.makeText(
                                    context,
                                    "Expense deleted",
                                    Toast.LENGTH_SHORT
                                ).show()

                                controller.popBackStack()
                            },
                            modifier = Modifier
                                .size(width = 55.dp, height = 55.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFCECEC),
                                contentColor = Color.Red
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
