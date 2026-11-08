package com.example.expensetracker.data.repository

import androidx.compose.runtime.saveable.rememberSaveable
import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.local.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository@Inject constructor(private val dao: ExpenseDao) {





    fun getAllExpense(): Flow<List<ExpenseEntity>> {

        return dao.getAllExpense()
    }


    suspend fun insert(expense: ExpenseEntity) = dao.insertExpense(expense)

    suspend fun update(expense: ExpenseEntity) = dao.updateExpense(expense)

    suspend fun delete(expense: ExpenseEntity) = dao.deleteExpense(expense)




}