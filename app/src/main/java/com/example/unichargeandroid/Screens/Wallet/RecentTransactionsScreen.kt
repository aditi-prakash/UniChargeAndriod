package com.example.unichargeandroid.Screens.Wallet

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unichargeandroid.Screens.Components.TransactionItem
import com.example.unichargeandroid.data.model.PaymentHistory
import com.example.unichargeandroid.ui.theme.UniChargeAndroidTheme
import com.example.unichargeandroid.viewmodels.AuthViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RecentTransactionsScreen(
    onBackClick: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val paymentHistory = currentUser?.paymentHistory ?: emptyList()

    // Sort transactions by date (newest first)
    val sortedTransactions = paymentHistory.sortedByDescending {
        parseDate(it.createdAt)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 22.dp, end = 22.dp, bottom = 20.dp),
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
            Spacer(Modifier.width(16.dp))

            Text(
                text = "Recent Transactions",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Transactions List or Empty State
        if (sortedTransactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No transactions yet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sortedTransactions) { transaction ->
                    TransactionItem(
                        status = getStatusDisplay(transaction.status),
                        title = getTransactionTitle(transaction),
                        price = formatAmount(transaction.amount, transaction.type),
                        date = formatDate(transaction.createdAt),
                        time = formatTime(transaction.createdAt),
                        priceColor = getAmountColor(transaction.type, transaction.status),
                        onClick = {
                            // You can add navigation to transaction details here
                        }
                    )
                }
            }
        }
    }
}

// Helper functions for formatting and display
private fun getStatusDisplay(status: String): String {
    return when (status.lowercase()) {
        "completed", "success" -> "Completed"
        "pending" -> "Pending"
        "failed", "declined" -> "Failed"
        "refunded" -> "Refunded"
        else -> status.replaceFirstChar { it.uppercase() }
    }
}

private fun getTransactionTitle(transaction: PaymentHistory): String {
    return when {
        transaction.stationName != null -> transaction.stationName
        transaction.vehicleName != null -> "Charging - ${transaction.vehicleName}"
        transaction.description != null -> transaction.description
        else -> when (transaction.type.lowercase()) {
            "charging" -> "EV Charging Session"
            "wallet_topup" -> "Wallet Top-up"
            "payment" -> "Payment"
            "refund" -> "Refund"
            else -> "Transaction"
        }
    }
}

private fun formatAmount(amount: Double, type: String): String {
    val sign = when (type.lowercase()) {
        "refund", "credit" -> "+"
        else -> "-"
    }
    return "$sign ₹${String.format("%.2f", amount)}"
}

private fun getAmountColor(type: String, status: String): Color {
    if (status.lowercase() == "failed") {
        return Color(0xFFFF3B30) // Red for failed transactions
    }

    return when (type.lowercase()) {
        "refund", "credit" -> Color(0xFF34C759) // Green for refunds/credits
        else -> Color(0xFFFF3B30) // Red for payments/debits
    }
}

private fun parseDate(dateString: String): Date {
    return try {
        // Try ISO format first
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateString)
            ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
            ?: Date()
    } catch (e: Exception) {
        Date() // Fallback to current date
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val date = parseDate(dateString)
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
    } catch (e: Exception) {
        "Unknown date"
    }
}

private fun formatTime(dateString: String): String {
    return try {
        val date = parseDate(dateString)
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date)
    } catch (e: Exception) {
        "Unknown time"
    }
}


@Preview(showBackground = true)
@Composable
fun RecentTransactionsScreenPreview() {
    UniChargeAndroidTheme {
        RecentTransactionsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    MaterialTheme {
        Column {
            TransactionItem(
                status = "Completed",
                title = "Walgreens - Brooklyn NY",
                price = "- ₹14.25",
                date = "Dec 17, 2024",
                time = "10:00 AM",
                priceColor = Color(0xFFFF3B30)
            )
            TransactionItem(
                status = "Refunded",
                title = "Wallet Top-up",
                price = "+ ₹500.00",
                date = "Dec 16, 2024",
                time = "02:30 PM",
                priceColor = Color(0xFF34C759)
            )
            TransactionItem(
                status = "Failed",
                title = "EV Station - Downtown",
                price = "- ₹75.50",
                date = "Dec 15, 2024",
                time = "09:15 AM",
                priceColor = Color(0xFFFF3B30)
            )
        }
    }
}