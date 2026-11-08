package com.example.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ExpenceViewModel @Inject constructor(private val repository: ExpenseRepository) :
    ViewModel() {


    val categoryList = listOf("Food", "Travel", "Shopping", "Bills")
    val paymentMethodList = listOf("Cash", "UPI", "Card")





    var date by mutableStateOf("")
        private set

    var amount by mutableStateOf("")
        private set


    var title by mutableStateOf("")
        private set
    var note by mutableStateOf("")
        private set
    var selectedCategory by mutableStateOf("Food")
        private set
    var selectedPayment by mutableStateOf("UPI")
        private set




    var time by mutableStateOf("")
        private set


    var month by mutableStateOf(0)
        private set

    fun onMonthChange(month1:Int){

        month = month1
    }
    fun onTimeChange(time1: String) {

        time = time1
    }

    fun onDateChange(day: String, month: String, year: String) {


        date = "$day/$month/$year"

    }


    fun onAmountChange(amount1: String) {

        amount = amount1


    }

    fun onTitleChange(title1: String) {

        title = title1
    }

    fun onNoteChange(note1: String) {

        note = note1

    }

    fun onCategoryChange(category1: String) {

        selectedCategory = category1
    }

    fun onPaymentChange(payment1: String) {

        selectedPayment = payment1
    }


    val allExpense: StateFlow<List<ExpenseEntity>> = repository.getAllExpense()
        .stateIn(

            viewModelScope, SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun addExpense(expense: ExpenseEntity) {

        viewModelScope.launch {

            repository.insert(expense)
        }
    }

    fun updateExpense(expense: ExpenseEntity) {

        viewModelScope.launch {

            repository.update(expense)

        }

    }


    fun deleteExpense(expense: ExpenseEntity) {

        viewModelScope.launch {

            repository.delete(expense)
        }
    }

    var total_spending by mutableFloatStateOf(0f)

    private

    fun update_spending_change(number: Float) {

        total_spending = number


    }


    fun saveExpenese() {


        if (title.isNotBlank() && amount.isNotBlank()) {


            val expenceEntiry = ExpenseEntity(

                id = 0,
                title = title,
                amount = amount.toDoubleOrNull() ?: return,
                category = selectedCategory,
                paymentMethod = selectedPayment,
                date = date,
                time = time,
                month = month,
                note = note

            )

            addExpense(expenceEntiry)


        }
    }


}



