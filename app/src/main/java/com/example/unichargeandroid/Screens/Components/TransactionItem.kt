package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionItem(
    status: String = "Paid",
    title: String,
    price: String,
    date: String,
    time: String,
    priceColor: Color = Color(0xFFFF3B30),
    onClick: () -> Unit = {}
) {

    val textColor = MaterialTheme.colorScheme.onSurface
    val surface = MaterialTheme.colorScheme.surface

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Status Pill with dynamic color based on status
            val statusColor = when (status.lowercase()) {
                "completed", "success", "paid" -> Color(0xFF34C759) to Color(0x1434C759) // Green
                "pending" -> Color(0xFFFF9500) to Color(0x14FF9500) // Orange
                "failed", "declined" -> Color(0xFFFF3B30) to Color(0x14FF3B30) // Red
                "refunded" -> Color(0xFF007AFF) to Color(0x14007AFF) // Blue
                else -> Color(0xFFFF3B30) to Color(0x14FF3B30) // Default red
            }

            Box(
                modifier = Modifier
                    .background(
                        color = statusColor.second,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = status,
                    color = statusColor.first,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = date,
                        fontSize = 14.sp,
                        color = textColor.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "  •  $time",
                        fontSize = 14.sp,
                        color = textColor.copy(alpha = 0.6f)
                    )
                }
            }

            // Price with dynamic color
            Text(
                text = price,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = priceColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = textColor.copy(alpha = 0.6f),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    MaterialTheme {
        TransactionItem(
            title = "Walgreens - Brooklyn NY",
            price = "- $14.25",
            date = "Dec 17, 2024",
            time = "10:00 AM"
        )
    }
}
