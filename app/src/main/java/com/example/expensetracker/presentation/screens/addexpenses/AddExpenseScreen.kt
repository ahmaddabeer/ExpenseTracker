package com.example.expensetracker.presentation.screens.add_expense_screen

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.presentation.screens.addexpenses.component.AddExpenseTopBar
import com.example.expensetracker.presentation.utils.getCurrentDateTime
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel

private val Green = Color(0xFF16855A)
private val DarkText = Color(0xFF202A24)
private val GreyText = Color(0xFF858585)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    controller: NavController
) {


    val expenceViewModel: ExpenceViewModel = hiltViewModel()

    val categoryList = expenceViewModel.categoryList
    val paymentList = expenceViewModel.paymentMethodList

    val context = LocalContext.current


    val current = getCurrentDateTime()

    val day = current["day"]
    val month = current["month"]
    val year = current["year"]
    val time = current["time"]

    val todaySelected = remember { mutableStateOf(false) }
    val choseDateSelected = remember { mutableStateOf(false) }

    /// date picker ka le a


    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState()













    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {

            AddExpenseTopBar(controller)
        }) { innerPadding ->


        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Spacer(Modifier.height(20.dp))

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Add Expense",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            }

            Text(
                "Enter your expense details below",
                color = GreyText,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(25.dp))

            // Amount
            Text("Amount", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = expenceViewModel.amount,
                onValueChange = { expenceViewModel.onAmountChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("0.00") },
                prefix = { Text("₹ ") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(18.dp))

            // Title
            Text("Expense Title", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = expenceViewModel.title,
                onValueChange = { expenceViewModel.onTitleChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. Lunch, Petrol") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(20.dp))

            // Category
            Text("Category", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(12.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                categoryList.chunked(2).forEach { rowCategories ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowCategories.forEach { category ->

                            val selected = expenceViewModel.selectedCategory == category

                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {

                                        expenceViewModel.onCategoryChange(category)
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selected)
                                        Color(0xFFE8F5EE)
                                    else Color(0xFFF8FAF8)
                                ),
                                border = if (selected)
                                    androidx.compose.foundation.BorderStroke(
                                        1.dp, Green
                                    )
                                else null
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        category,
                                        color = if (selected) Green else DarkText,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Payment Method
            Text("Payment Method", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                paymentList.forEach { payment ->

                    FilterChip(
                        selected = expenceViewModel.selectedPayment == payment,

                        onClick = { expenceViewModel.onPaymentChange(payment) },
                        label = { Text(payment) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFE8F5EE),
                            selectedLabelColor = Green
                        )
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Date
            if (todaySelected.value) {

                Text("$day/$month/$year", fontWeight = FontWeight.SemiBold)

            } else if (choseDateSelected.value) {

                Text(expenceViewModel.date, fontWeight = FontWeight.SemiBold)

            } else {

                Text("Date", fontWeight = FontWeight.SemiBold)


            }

            Spacer(Modifier.height(8.dp))




            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = {

                        todaySelected.value = true
                        choseDateSelected.value = false


                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = if (todaySelected.value) BorderStroke(
                        1.dp,
                        Color.Green
                    ) else BorderStroke(1.dp, Color.Black)

                ) {
                    Text("Today", color = DarkText)
                }

//                OutlinedButton(
//                    onClick = {
//                        todaySelected.value = false
//                        choseDateSelected.value = true
//
//                    },
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(12.dp),
//                    border= if(choseDateSelected.value)BorderStroke(1.dp,Color.Green) else BorderStroke(1.dp,Color.Black)
//
//
//                ) {
//                    Text("Choose Date", color = DarkText)
//                }


                OutlinedButton(
                    onClick = {
                        showDatePicker = true


                        expenceViewModel.onDateChange(
                            day.toString(),
                            month.toString(),
                            year.toString()
                        )

                        expenceViewModel.onMonthChange(
                            month1 = month?.toInt() ?: 0
                        )
                        expenceViewModel.onTimeChange(time.toString())




                        choseDateSelected.value = true
                        todaySelected.value = false
                    },

                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = if (choseDateSelected.value) BorderStroke(
                        1.dp,
                        Color.Green
                    ) else BorderStroke(1.dp, Color.Black)

                ) {
                    Text("Choose Date")
                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val calendar = Calendar.getInstance(
                                        TimeZone.getTimeZone("UTC")
                                    )
                                    calendar.timeInMillis = millis

                                    expenceViewModel.onDateChange(
                                        calendar.get(Calendar.DAY_OF_MONTH).toString(),
                                        (calendar.get(Calendar.MONTH) + 1).toString(),
                                        calendar.get(Calendar.YEAR).toString()
                                    )

                                    expenceViewModel.onMonthChange(
                                        month1 = calendar.get(Calendar.MONTH) + 1
                                    ).toString()

                                    expenceViewModel.onTimeChange(time.toString())


                                }
                                showDatePicker = false
                            }) {
                                Text("OK")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Note
            Text("Note (Optional)", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = expenceViewModel.note,
                onValueChange = { expenceViewModel.onNoteChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                placeholder = { Text("Write something...") },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(25.dp))

            // Save Button
            Button(
                onClick = {
                    expenceViewModel.saveExpenese()
                    expenceViewModel.onAmountChange("")
                    expenceViewModel.onTitleChange("")
                    expenceViewModel.onNoteChange("")





                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()


                },
                enabled = expenceViewModel.amount.isNotBlank() && expenceViewModel.title.isNotBlank() && todaySelected.value != choseDateSelected.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                )
            ) {
                Text(
                    "Save Expense",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(25.dp))
        }


    }


}
