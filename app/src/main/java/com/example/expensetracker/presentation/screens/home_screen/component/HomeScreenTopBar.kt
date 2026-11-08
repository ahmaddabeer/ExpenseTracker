
package com.example.expensetracker.presentation.screens.home_screen.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetracker.presentation.navigation.Route
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.PhotoCirclePlus
import dev.seyfarth.tablericons.outlined.UserCircle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(controller: NavController) {

    TopAppBar(
        title = {
            Text(
                text = "My Profile",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202A24)
            )
        },

        actions = {
            IconButton(
                onClick = {
                    // Profile navigation later

                    controller.navigate(Route.Profile)
                }
            ) {
                Icon(
                    imageVector = TablerIcons.Outlined.UserCircle,
                    contentDescription = "Profile",
                    tint = Color(0xFF16855A)
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}
