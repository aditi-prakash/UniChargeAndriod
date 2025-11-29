package com.example.unichargeandroid.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.sp

@Composable
fun NotificationCard(
    title: String?,
    message: String?,
    time: String?,
    isUnread: Boolean = false,
    notificationType: String? = "general",
    onClick: () -> Unit = {}
) {

    // Safe values with defaults
    val safeTitle = title ?: "Notification"
    val safeMessage = message ?: "No message content"
    val safeTime = time ?: "Unknown time"
    val safeType = notificationType ?: "general"

    val icon = when (safeType) {
        "charging", "charging_complete", "charging_started" -> Icons.Filled.EvStation
        "reward", "loyalty" -> Icons.Filled.CardGiftcard
        "booking" -> Icons.Filled.CalendarToday
        "payment" -> Icons.Filled.Payment
        "warning" -> Icons.Filled.Warning
        "info" -> Icons.Filled.Info
        else -> Icons.Filled.Notifications
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isUnread) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {

            // Notification Icon
            Icon(
                imageVector = icon,
                contentDescription = "Notification type",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                // Title with unread indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = safeTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )

                    if (isUnread) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = safeMessage,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = safeTime,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationCardPreview() {
    UniChargeAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            NotificationCard(
                title = "Charging Complete",
                message = "Your vehicle has been fully charged. Total cost: ₹245.50",
                time = "2 hours ago",
                isUnread = true,
                notificationType = "charging_complete"
            )
        }
    }
}