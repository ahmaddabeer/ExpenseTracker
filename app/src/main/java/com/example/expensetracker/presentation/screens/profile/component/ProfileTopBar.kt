
package com.example.expensetracker.presentation.screens.profile_screen.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetracker.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    controller: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = "My Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202A24)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    controller.navigate(Route.HomeScreen) {
                        popUpTo(Route.HomeScreen) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    tint = Color(0xFF202A24)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}
