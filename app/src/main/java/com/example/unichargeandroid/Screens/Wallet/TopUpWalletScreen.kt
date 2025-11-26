package com.example.unichargeandroid.Screens.Wallet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme

@Composable
fun TopUpWalletScreen() {

    var selectedMethod by remember { mutableStateOf("UPI") }
    val methods = listOf("UPI", "Credit Card", "Debit Card", "Net Banking")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(top = 50.dp, start = 22.dp, end = 22.dp)
    ) {

        // 🔙 Back + Title row
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* handle back */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Top Up Wallet",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Select Payment Method",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Payment Method List
        methods.forEach { method ->
            PaymentMethodItem(
                method = method,
                selected = selectedMethod == method,
                onSelect = { selectedMethod = method }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Add New Method Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFDFFFE2) // Light green background
            ),
            shape = RoundedCornerShape(28.dp),
            border = BorderStroke(
                width = 2.dp,
                color = Color(0xFF1B8A3D) // Green border
            )
        ) {
            Text(
                text = "+ Add New Method",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1B8A3D) // Green text
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Push Continue Button to bottom if content grows
        Spacer(modifier = Modifier.weight(1f))

        // Continue Button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp)) // bottom padding
    }
}


@Preview(showBackground = true)
@Composable
fun TopUpWalletScreenPreview() {
    UniChargeAndroidTheme {
        TopUpWalletScreen()
    }
}
