package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrivacyPolicyScreen(
    onBackClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .padding(top = 50.dp)
            .verticalScroll(scrollState)
    ) {
        // Header Row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(26.dp).clickable(
                    onClick = onBackClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
                colorFilter = ColorFilter.tint(colors.onBackground)
            )

            Spacer(Modifier.width(16.dp))

            Text(
                "Privacy Policy",
                style = typography.titleLarge,
                color = colors.onBackground
            )
        }

        Spacer(Modifier.height(24.dp))

        // Intro description
        Text(
            text = "At UniCharge, we are committed to ensuring the privacy and security of your information. Please take a moment to read how we handle, protect, and use the data you provide.",
            style = typography.bodyMedium,
            color = colors.onSurfaceVariant,
            lineHeight = 24.sp,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(26.dp))

        // Policy sections
        PolicySection(
            title = "1. Information We Collect",
            desc = "We collect personal details such as your name, email, phone number, vehicle information, location data, and usage details to provide an enhanced charging experience."
        )

        PolicySection(
            title = "2. How Your Data Is Used",
            desc = "Your information helps us provide reliable charging services, process bookings, ensure app functionality, and improve overall user experience."
        )

        PolicySection(
            title = "3. Data Sharing",
            desc = "Information is shared only with trusted service providers or legal authorities when necessary. UniCharge does not sell your personal data."
        )

        PolicySection(
            title = "4. Security Measures",
            desc = "We implement encryption, secure servers, and continuous monitoring to protect your personal information from unauthorized access."
        )

        PolicySection(
            title = "5. Your Rights",
            desc = "You may request to view, update, or delete your data at any time. You can also close your account through our Help Center."
        )

        PolicySection(
            title = "6. Contact Us",
            desc = "For any privacy-related questions or requests, contact our support team directly via the Help Center."
        )

        Spacer(Modifier.height(50.dp))
    }
}

@Composable
fun PolicySection(title: String, desc: String) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Text(
        text = title,
        style = typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = colors.onBackground
    )

    Spacer(Modifier.height(8.dp))

    Text(
        text = desc,
        style = typography.bodyMedium,
        color = colors.onSurfaceVariant,
        lineHeight = 23.sp,
        textAlign = TextAlign.Start
    )

    Spacer(Modifier.height(22.dp))
}