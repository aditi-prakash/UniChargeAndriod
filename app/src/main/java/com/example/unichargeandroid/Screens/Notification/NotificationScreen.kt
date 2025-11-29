package com.example.unichargeandroid.Screens.Notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.unichargeandroid.Screens.Components.NotificationCard
import com.example.unichargeandroid.viewmodels.AuthViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    val currentUser by authViewModel.currentUser.collectAsState()
    val notifications = currentUser?.notifications ?: emptyList()

    // Debug: Print notifications to log
    LaunchedEffect(notifications) {
        println("=== NOTIFICATIONS DEBUG ===")
        println("Total notifications: ${notifications.size}")
        notifications.forEachIndexed { index, notification ->
            println("Notification $index:")
            println("  - Title: '${notification.title}'")
            println("  - Message: '${notification.message}'")
            println("  - Type: '${notification.type}'")
            println("  - isRead: ${notification.isRead}")
            println("  - CreatedAt: '${notification.createdAt}'")
        }
        println("=== END DEBUG ===")
    }

    // Count unread notifications safely
    val unreadCount = notifications.count { notification ->
        notification.isRead == false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp)
    ) {

        // Top bar
        TopBar(
            unreadCount = unreadCount,
            onBackClick = { navController.popBackStack() }
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {

            Spacer(modifier = Modifier.height(18.dp))

            MessagesSection(
                notifications = notifications,
                onNotificationClick = { notification ->
                    // Simple click handler
                }
            )
        }
    }
}

// TOP BAR
@Composable
fun TopBar(
    unreadCount: Int,
    onBackClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(26.dp)
                .clickable(
                    onClick = onBackClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "Notifications",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = getNotificationSubtitle(unreadCount),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun MessagesSection(
    notifications: List<com.example.unichargeandroid.data.model.Notification>,
    onNotificationClick: (com.example.unichargeandroid.data.model.Notification) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {

        Text(
            text = "Messages",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(22.dp))

        if (notifications.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No messages available",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your notifications will appear here",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notifications) { notification ->
                    NotificationCard(
                        title = notification.title,
                        message = notification.message,
                        time = formatNotificationTime(notification.createdAt),
                        isUnread = notification.isRead == false,
                        notificationType = notification.type,
                        onClick = { onNotificationClick(notification) }
                    )
                }
            }
        }
    }
}

// Helper functions
private fun getNotificationSubtitle(unreadCount: Int): String {
    return when (unreadCount) {
        0 -> "You're all caught up!"
        1 -> "You have 1 unread notification"
        else -> "You have $unreadCount unread notifications"
    }
}

private fun formatNotificationTime(dateString: String?): String {
    if (dateString.isNullOrEmpty()) {
        return "Unknown time"
    }

    return try {
        val date = parseDate(dateString)
        val now = Date()
        val diff = now.time - date.time

        when {
            diff < 60000 -> "Just now"
            diff < 3600000 -> "${diff / 60000} min ago"
            diff < 86400000 -> "${diff / 3600000} hr ago"
            diff < 604800000 -> "${diff / 86400000} days ago"
            else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
        }
    } catch (e: Exception) {
        "Some time ago"
    }
}

private fun parseDate(dateString: String): Date {
    return try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateString)
            ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
            ?: Date()
    } catch (e: Exception) {
        Date()
    }
}