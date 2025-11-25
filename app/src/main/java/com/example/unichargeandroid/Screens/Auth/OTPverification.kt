package com.example.unichargeandroid.Screens.Auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpVerificationScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 20.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // 🔙 Back Arrow
        IconButton(onClick = { /* NO ACTION */ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "OTP code verification 🔒",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Subtext
        Text(
            text = "We have sent an OTP code to email.\nEnter the OTP code below to continue.",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(25.dp))

        // OTP BOXES
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OtpBox("8")
            OtpBox("2")
            OtpBox("5", highlighted = true)
            OtpBox("")
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Didn't receive email text
        Text(
            text = "Didn't receive email?",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Resend timer
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "You can resend code in ", color = Color.Black, fontSize = 14.sp)
            Text(
                text = "48 s",
                color = Color(0xFF18C77B),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Add some spacing at bottom
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun OtpBox(value: String, highlighted: Boolean = false) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .border(
                width = if (highlighted) 2.dp else 1.dp,
                color = if (highlighted) Color(0xFF18C77B) else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun OtpVerificationScreenPreview() {
    MaterialTheme {
        OtpVerificationScreen()
    }
}
