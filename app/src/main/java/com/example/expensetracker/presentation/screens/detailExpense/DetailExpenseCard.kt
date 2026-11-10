package com.example.expensetracker.presentation.screens.detailExpense

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.presentation.navigation.Route
import com.example.expensetracker.presentation.screens.detailExpense.component.TopBarDetailExpense
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Edit
import dev.seyfarth.tablericons.outlined.HttpDelete
import dev.seyfarth.tablericons.outlined.ToolsKitchen

@Composable
fun DetailExpenseCard(controller: NavController,
                      expId:Int

) {



    val UpdatedAt = System.currentTimeMillis()


    val viewModel: ExpenceViewModel = hiltViewModel()

    val expense1 by viewModel.allExpense.collectAsState()

    val expense = expense1.find { it.id == expId}

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

            TopBarDetailExpense(controller,{controller.popBackStack()},{})
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        )
        {

            // Category Icon and Amount
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .background(
                                color = Color.White.copy(0.5f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = TablerIcons.Outlined.ToolsKitchen,
                            contentDescription = "Expence Detail Icon"
                        )
                    }

                    Text(
                        text = "₹ ${expense?.amount}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "${expense?.category}",
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
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Title", color = Color.Gray)
                    Text("${expense?.title}", color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Category", color = Color.Gray)
                    Text("${expense?.category}", color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Payment Method", color = Color.Gray)
                    Text("${expense?.paymentMethod}", color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("CreatedAt", color = Color.Gray)
                    Text("${expense?.date}", color = Color.Black)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("UpdatedAt", color = Color.Gray)
                    Text("${expense?.date}", color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Time", color = Color.Gray)
                    Text("${expense?.time}", color = Color.Black)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Month", color = Color.Gray)
                    Text("${expense?.month}", color = Color.Black)
                }
            }

            // Note
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Note",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${expense?.note}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }


            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {

                        controller.navigate(Route.EditExpenses(expId)){

                            popUpTo<Route.DetailExpenseCard>{
                                inclusive = true
                            }
                            launchSingleTop = true
                        }


                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
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


                        if(expense != null){

                            viewModel.deleteExpense(expense)
                        }
                        Toast.makeText(context,"Item Deleting....",Toast.LENGTH_SHORT).show()
                        controller.navigate(Route.HomeScreen){

                            popUpTo<Route.HomeScreen>{

                                inclusive = false
                            }
                            launchSingleTop = true
                        }







                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width(55.dp),
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



