package com.example.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense:ExpenseEntity)


    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    
    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("Select * from expense order by id desc")
    fun getAllExpense(): Flow<List<ExpenseEntity>>

}