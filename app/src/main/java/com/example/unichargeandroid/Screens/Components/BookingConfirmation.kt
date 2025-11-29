package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme

@Composable
fun BookingConfirmation(
    stationName: String,
    date: String,
    onOkClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------- SUCCESS ICON ----------
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF34C759),
                modifier = Modifier.size(60.dp)
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Successful Booking",
                style = MaterialTheme.typography.titleLarge,
                color = colors.onSurface,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Your charging session is now confirmed.",
                fontSize = 14.sp,
                color = colors.onSurface.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(22.dp))

            // ---------- BOOKING DETAILS ----------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colors.surfaceVariant.copy(alpha = 0.3f),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {

                DetailRow(icon = Icons.Default.LocalGasStation, title = "Station", value = stationName)

                Spacer(Modifier.height(12.dp))

                DetailRow(icon = Icons.Default.CalendarMonth, title = "Date", value = date)
            }

            Spacer(Modifier.height(24.dp))

            // ---------- OK BUTTON ----------
            Button(
                onClick = onOkClick,
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("OK", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun DetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, value: String) {
    val colors = MaterialTheme.colorScheme

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(10.dp))
        Column {
            Text(title, fontSize = 12.sp, color = colors.onSurface.copy(alpha = 0.6f))
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = colors.onSurface)
        }
    }
}


