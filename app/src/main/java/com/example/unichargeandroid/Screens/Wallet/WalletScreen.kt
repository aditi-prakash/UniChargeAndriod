package com.example.unichargeandroid.Screens.Wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.unichargeandroid.Routes
import com.example.unichargeandroid.Screens.Components.BottomNavBar

@Composable
fun WalletScreen(navController: NavController) {

    var selectedMethod by remember { mutableStateOf("UPI") }
    val methods = listOf("UPI", "Credit Card", "Debit Card", "Net Banking")

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomNavBar(navController, Routes.WalletScreen) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background) // theme BG
                .padding(top = 15.dp, start = 18.dp, end = 18.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "My Wallet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground // readable text
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Wallet Card UI (no changes here)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF18C77B), Color(0xFF13B26A))
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(18.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Andrew Ainsley",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Text(
                                text = "•••• •••• •••• 0099",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(24.dp).background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(50)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(Modifier.size(24.dp).background(Color(0xFFFF6400), RoundedCornerShape(50)))
                        }
                    }

                    Column {
                        Text(text = "Your balance", fontSize = 12.sp, color = Color.White)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "₹957.50", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Top Up", color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Payment Methods",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            methods.forEach { method ->
                PaymentMethodItem(
                    method = method,
                    selected = selectedMethod == method,
                    onSelect = { selectedMethod = method }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun PaymentMethodItem(
    method: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
                else MaterialTheme.colorScheme.surface,
                RoundedCornerShape(12.dp)
            )
            .border(
                if (selected) 2.dp else 1.dp,
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(2.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, CircleShape)
                .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent, CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = method,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}