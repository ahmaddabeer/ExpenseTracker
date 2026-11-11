
package com.example.expensetracker.presentation.screens.editexpenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel

private val PrimaryGreen = Color(0xFF16855A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenses(
    controller: NavController,
    eId: Int
) {



    val viewModel: ExpenceViewModel = hiltViewModel()

    val expenceList by viewModel.allExpense.collectAsState()

    val expense = expenceList.find { it.id == eId }

    LaunchedEffect(expense?.id) {

        expense?.let {

            viewModel.onAmountChange(it.amount.toString())
            viewModel.onPaymentChange(it.paymentMethod)
            viewModel.onCategoryChange(it.category)
            viewModel.onTitleChange(it.title)
            viewModel.onNoteChange(it.note)
            viewModel.onCreatedAt(it.createdAt.toString())

        }
    }








    val categories = listOf(
        "Food", "Travel", "Shopping", "Bills"
    )

    val paymentMethods = listOf(
        "Cash", "UPI", "Card"
    )

    if(expense == null){

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Expense not found")
        }


    }else {

        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Edit Expense",
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                controller.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },

            bottomBar = {
                Surface(
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    Button(
                        onClick = {
                            // Update logic baad mein add karenge

                            viewModel.onUpdatedAt(System.currentTimeMillis().toString())
                            viewModel.onIdchange(eId)
                            viewModel.onupdateExpense()
                            controller.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 12.dp
                            )
                            .height(54.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreen
                        )
                    ) {
                        Text(
                            text = "Update Expense",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 20.dp),

                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // Amount

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Amount",
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = viewModel.amount,
                        onValueChange = { viewModel.onAmountChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Enter amount")
                        },
                        prefix = {
                            Text("₹ ")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Title

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Expense Title",
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = viewModel.title,
                        onValueChange = { viewModel.onTitleChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Enter expense title")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Category

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Category",
                        fontWeight = FontWeight.SemiBold
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        categories.forEach { item ->

                            FilterChip(
                                selected = viewModel.selectedCategory == item,
                                onClick = {
                                    viewModel.onCategoryChange(item)
                                },
                                label = {
                                    Text(item)
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFD8F2E2),
                                    selectedLabelColor = PrimaryGreen
                                )
                            )
                        }
                    }
                }

                // Payment Method

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Payment Method",
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        paymentMethods.forEach { item ->

                            FilterChip(
                                selected = viewModel.selectedPayment == item,
                                onClick = {
                                    viewModel.onPaymentChange(item)
                                },
                                label = {
                                    Text(item)
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFD8F2E2),
                                    selectedLabelColor = PrimaryGreen
                                )
                            )
                        }
                    }
                }

                // Note

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Note",
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = viewModel.note,
                        onValueChange = { viewModel.onNoteChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 110.dp),
                        placeholder = {
                            Text("Add a note (optional)")
                        },
                        shape = RoundedCornerShape(12.dp),
                        minLines = 3
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
