package com.example.unichargeandroid.Screens.Home.HelpCenter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HelpCenterScreenUI() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var search by remember { mutableStateOf("") }

    // State for each FAQ
    var expand1 by remember { mutableStateOf(false) }
    var expand2 by remember { mutableStateOf(false) }
    var expand3 by remember { mutableStateOf(false) }
    var expand4 by remember { mutableStateOf(false) }
    var expand5 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp, vertical = 40.dp)
            .verticalScroll(rememberScrollState()) // Scrollable if content exceeds screen
    ) {

        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = colors.onBackground, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(14.dp))
            Text("Help Center", style = typography.titleLarge, color = colors.onBackground)
        }

        Spacer(Modifier.height(20.dp))

        // Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("FAQ", style = typography.titleMedium, color = colors.primary)
            Text("Contact us", style = typography.titleMedium, color = colors.onSurfaceVariant)
        }

        Spacer(Modifier.height(20.dp))

        // Search Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(colors.surfaceVariant, RoundedCornerShape(28.dp))
                .padding(horizontal = 16.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = colors.onSurfaceVariant)
            Spacer(Modifier.width(10.dp))
            Text(
                if (search.isEmpty()) "Search" else search,
                color = if (search.isEmpty()) colors.onSurfaceVariant else colors.onSurface,
                fontSize = 15.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        // FAQ cards with expandable descriptions
        FAQCard(
            title = "What is UniCharge?",
            description = "UniCharge is a mobile application that allows users to locate, book, and pay for EV charging stations easily. It provides real-time availability and pricing information for EV chargers across your city.",
            expanded = expand1,
            onExpandChange = { expand1 = !expand1 }
        )
        Spacer(Modifier.height(12.dp))

        FAQCard(
            title = "Is the UniCharge app free?",
            description = "Yes, UniCharge is completely free to download and use. Some premium services like membership plans or faster charging options may incur additional charges depending on the station provider.",
            expanded = expand2,
            onExpandChange = { expand2 = !expand2 }
        )
        Spacer(Modifier.height(12.dp))

        FAQCard(
            title = "How can I make a station booking?",
            description = "To book a charging station, select your desired station on the map, choose the available time slot, and confirm your booking. You can pay directly through the app using your preferred payment method.",
            expanded = expand3,
            onExpandChange = { expand3 = !expand3 }
        )
        Spacer(Modifier.height(12.dp))

        FAQCard(
            title = "How can I log out from UniCharge?",
            description = "You can log out by going to the Account section, scrolling to the bottom, and tapping on the 'Logout' button. This will safely sign you out of your UniCharge account.",
            expanded = expand4,
            onExpandChange = { expand4 = !expand4 }
        )
        Spacer(Modifier.height(12.dp))

        FAQCard(
            title = "How do I close my UniCharge account?",
            description = "If you wish to permanently close your UniCharge account, please contact our support team through the 'Contact us' section in the app. Make sure to settle any pending payments before requesting account closure.",
            expanded = expand5,
            onExpandChange = { expand5 = !expand5 }
        )
    }
}

@Composable
fun FAQCard(
    title: String,
    description: String,
    expanded: Boolean,
    onExpandChange: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface, RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandChange() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = typography.bodyLarge, color = colors.onSurface)
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = colors.primary
            )
        }

        if (expanded) {
            Spacer(Modifier.height(8.dp))
            Text(description, style = typography.bodyMedium, color = colors.onSurfaceVariant)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHelpCenterScreenUI() {
    HelpCenterScreenUI()
}
