package com.example.expensetracker.presentation.screens.editexpenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetracker.presentation.viewmodel.ExpenceViewModel
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenses(controller: NavController,eId:Int){



    val viewModel: ExpenceViewModel = hiltViewModel()

    val expense1 by viewModel.allExpense.collectAsState()

    val expense = expense1.find { it.id == eId}

    val context = LocalContext.current


    LaunchedEffect(expense?.id) {

        expense?.let {

            viewModel.onAmountChange(it.amount.toString())
            viewModel.onTitleChange(it.title)
            viewModel.onCategoryChange(it.category)
            viewModel.onPaymentChange(it.paymentMethod)


        }
    }


    Scaffold(modifier = Modifier.fillMaxWidth(),

        topBar = {

            CenterAlignedTopAppBar(
                title = {

                    Text(text = "Edit Your Expenses")


                },
                navigationIcon = {

                    Icon(imageVector = TablerIcons.Outlined.ArrowBack, contentDescription = "Back to home")


                },
                actions = {




                }
            )
        }) {innerPadding->

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {




            Text(text = "${expense?.title}")
        }


    }
}