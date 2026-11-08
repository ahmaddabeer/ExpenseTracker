package com.example.expensetracker.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getCurrentDateTime(): Map<String, String> {

    val date = Date()

    return mapOf(

        "day" to SimpleDateFormat("dd", Locale.getDefault()).format(date),
        "month" to SimpleDateFormat("MM", Locale.getDefault()).format(date),
        "year" to SimpleDateFormat("yyyy", Locale.getDefault()).format(date),
        "time" to SimpleDateFormat("hh:mm a",Locale.getDefault()).format(date)


    )
}