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
    val createdAt:Long,
    val updatedAt: Long,
    val note: String,


)