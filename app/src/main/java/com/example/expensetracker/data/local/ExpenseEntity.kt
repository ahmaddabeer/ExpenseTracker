package com.example.expensetracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class ExpenseEntity(



    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val amount: Double?,
    val category: String,
    val paymentMethod: String,
    val date: String,  // "07/11/2026"
    val time: String,  // "06:30 PM"
    val month: Int,  // 11
    val note: String,


)