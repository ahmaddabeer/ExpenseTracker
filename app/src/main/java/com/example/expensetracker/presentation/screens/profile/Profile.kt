
package com.example.expensetracker.presentation.screens.profile_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.expensetracker.presentation.screens.profile_screen.component.ProfileTopBar

private val Green = Color(0xFF16855A)
private val LightGreen = Color(0xFFE8F5EE)
private val DarkText = Color(0xFF202A24)
private val GreyText = Color(0xFF858585)

@Composable
fun Profile(controller: NavController) {

    var profileImage by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        profileImage = uri
    }

    Scaffold(
        topBar = {
            ProfileTopBar(controller = controller)
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            // Profile Image
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {

                    if (profileImage != null) {
                        AsyncImage(
                            model = profileImage,
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .background(LightGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Green,
                                modifier = Modifier.size(65.dp)
                            )
                        }
                    }

                    // Camera Button
                    IconButton(
                        onClick = {
                            imagePicker.launch("image/*")
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .background(Green, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = Color.White,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    text = "Your Name",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Your Occupation",
                    fontSize = 14.sp,
                    color = GreyText
                )

                Spacer(Modifier.height(20.dp))
            }

            // Personal Information
            Text(
                text = "Personal Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(Modifier.height(15.dp))

            ProfileDetailItem(
                icon = Icons.Default.Person,
                title = "Full Name",
                value = "Your Full Name"
            )

            ProfileDetailItem(
                icon = Icons.Default.Cake,
                title = "Age",
                value = "Your Age"
            )

            ProfileDetailItem(
                icon = Icons.Default.Work,
                title = "Occupation",
                value = "Your Occupation"
            )

            ProfileDetailItem(
                icon = Icons.Default.Email,
                title = "Email Address",
                value = "your@email.com"
            )

            ProfileDetailItem(
                icon = Icons.Default.Phone,
                title = "Phone Number",
                value = "Your Phone Number"
            )

            ProfileDetailItem(
                icon = Icons.Default.LocationOn,
                title = "Location",
                value = "Your City, Country"
            )

            Spacer(Modifier.height(15.dp))

            // Account Information
            Text(
                text = "Account Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(Modifier.height(15.dp))

            ProfileDetailItem(
                icon = Icons.Default.CurrencyRupee,
                title = "Currency",
                value = "Indian Rupee (INR)"
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ProfileDetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(46.dp)
                .background(
                    LightGreen,
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Green
            )
        }

        Spacer(Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                color = GreyText
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )
        }
    }
}
