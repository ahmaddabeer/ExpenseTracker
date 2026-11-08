package com.example.expensetracker.presentation.screens.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetracker.presentation.component.bottom_nav_bar.BottomNavBar

private val Green = Color(0xFF16855A)
private val DarkText = Color(0xFF202A24)
private val GreyText = Color(0xFF858585)

@Composable
fun SettingScreen(controller: NavController) {

 var notificationsEnabled by remember {
  mutableStateOf(true)
 }

 var darkModeEnabled by remember {
  mutableStateOf(false)
 }



 Scaffold(modifier = Modifier.fillMaxSize(),

     bottomBar = {

         BottomNavBar(controller)
     }) {innerPadding->

  Column(
   modifier = Modifier
    .padding(innerPadding)
    .fillMaxSize()
    .background(Color.White)
    .padding(20.dp)
  )
  {

   Text(
    "Settings",
    fontSize = 25.sp,
    fontWeight = FontWeight.Bold,
    color = DarkText
   )

   Text(
    "Manage your app preferences",
    fontSize = 14.sp,
    color = GreyText
   )

   Spacer(Modifier.height(25.dp))

   Text(
    "Preferences",
    fontSize = 17.sp,
    fontWeight = FontWeight.Bold,
    color = DarkText
   )

   Spacer(Modifier.height(12.dp))

   SettingsSwitchItem(
    title = "Notifications",
    subtitle = "Receive expense reminders",
    checked = notificationsEnabled,
    onCheckedChange = {
     notificationsEnabled = it
    }
   )

   HorizontalDivider(color = Color(0xFFEEEEEE))

   SettingsSwitchItem(
    title = "Dark Mode",
    subtitle = "Change app appearance",
    checked = darkModeEnabled,
    onCheckedChange = {
     darkModeEnabled = it
    }
   )

   HorizontalDivider(color = Color(0xFFEEEEEE))

   Spacer(Modifier.height(25.dp))

   Text(
    "General",
    fontSize = 17.sp,
    fontWeight = FontWeight.Bold,
    color = DarkText
   )

   Spacer(Modifier.height(12.dp))

   SettingsTextItem("Currency", "Indian Rupee (₹)")
   SettingsTextItem("Privacy Policy", "View privacy policy")
   SettingsTextItem("About App", "Expense Tracker v1.0")

   Spacer(Modifier.height(25.dp))

   OutlinedButton(
    onClick = {},
    modifier = Modifier
     .fillMaxWidth()
     .height(50.dp),
    shape = RoundedCornerShape(12.dp)
   ) {
    Text(
     "Logout",
     color = Color.Red
    )
   }
  }
 }





}


@Composable
private fun SettingsSwitchItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                title,
                fontWeight = FontWeight.Medium,
                color = DarkText
            )

            Text(
                subtitle,
                fontSize = 12.sp,
                color = GreyText
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Green
            )
        )
    }
}

@Composable
private fun SettingsTextItem(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 14.dp)
    ) {
        Text(
            title,
            fontWeight = FontWeight.Medium,
            color = DarkText
        )

        Text(
            subtitle,
            fontSize = 12.sp,
            color = GreyText
        )
    }

    HorizontalDivider(color = Color(0xFFEEEEEE))
}
