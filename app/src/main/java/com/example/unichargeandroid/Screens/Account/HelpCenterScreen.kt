package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unichargeandroid.Screens.Account.ContactUsSection

@Composable
fun HelpCenterScreen() {

    var selectedTab by remember { mutableStateOf("FAQ") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp, vertical = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {

        /** ---------------------- TOP BAR ---------------------- */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(14.dp))

            Text(
                "Help Center",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(20.dp))

        /** ---------------------- TABS ---------------------- */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // FAQ TAB
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { selectedTab = "FAQ" }
            ) {
                Text(
                    "FAQ",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (selectedTab == "FAQ") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (selectedTab == "FAQ") {
                    Spacer(Modifier.height(4.dp))
                    Box(
                        Modifier
                            .height(2.dp)
                            .width(70.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }

            // CONTACT TAB
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { selectedTab = "Contact" }
            ) {
                Text(
                    "Contact us",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (selectedTab == "Contact") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (selectedTab == "Contact") {
                    Spacer(Modifier.height(4.dp))
                    Box(
                        Modifier
                            .height(2.dp)
                            .width(80.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        /** ---------------------- TAB CONTENT ---------------------- */

        if (selectedTab == "FAQ") {
            FAQSection()
        } else {
            ContactUsSection()
        }
    }
}


@Composable
fun FAQSection() {

    var expand1 by remember { mutableStateOf(false) }
    var expand2 by remember { mutableStateOf(false) }
    var expand3 by remember { mutableStateOf(false) }
    var expand4 by remember { mutableStateOf(false) }
    var expand5 by remember { mutableStateOf(false) }

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
        title = "How do I close account?",
        description = "If you wish to permanently close your UniCharge account, please contact our support team through the 'Contact us' section in the app. Make sure to settle any pending payments before requesting account closure.",
        expanded = expand5,
        onExpandChange = { expand5 = !expand5 }
    )
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { onExpandChange() },
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