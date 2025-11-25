package com.example.app.ui.screens.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unichargeandroid.R

@Composable
fun AddVehicleScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Personalize your\nexperience by adding a\nvehicle 🚗",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            color = Color(0xFF1A1A1A),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Your vehicle is used to determine compatible\ncharging stations.",
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = Color(0xFF6F6F6F)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.vehicle),
            contentDescription = "Vehicle Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), // reduced height to fit screen
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEFF8F4),
                    contentColor = Color(0xFF00C853)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Add Later",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00C853),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Add Vehicle",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
fun AddVehicleScreenPreview() {
    MaterialTheme {
        AddVehicleScreen()
    }
}
