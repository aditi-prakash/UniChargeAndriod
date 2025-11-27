package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.unichargeandroid.R

@Composable
fun AboutScreen(
    onBackClick: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 22.dp, end = 22.dp, top = 50.dp)
            .verticalScroll(scrollState)
    ) {

        // Top Bar
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
                text = "About UniCharge",
                fontSize = 22.sp,
                fontWeight = typography.titleLarge.fontWeight,
                color = colors.onBackground
            )
        }

        Spacer(Modifier.height(15.dp))

        // EVPoint Logo
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.unicharge_logo),
                contentDescription = "UniCharge Logo",
                modifier = Modifier.size(110.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        // Version
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "v1.0.0",
                fontSize = 16.sp,
                fontWeight = typography.bodyMedium.fontWeight,
                color = colors.onBackground
            )
        }

        Spacer(Modifier.height(20.dp))

        // Divider line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colors.outlineVariant)
        )

        Spacer(Modifier.height(18.dp))

        // List Items
        val aboutItems = listOf(
            "Job Vacancy", "Developer", "Partner", "Accessibility",
            "Terms of Use", "Feedback", "Rate us",
            "Visit Our Website", "Follow us on Social Media"
        )

        Column {
            aboutItems.forEach { item ->
                AboutListItem(item)
            }
        }
    }
}

@Composable
fun AboutListItem(title: String) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontSize = 17.sp,
            fontWeight = typography.bodyLarge.fontWeight,
            color = colors.onSurface
        )
        Image(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Navigate",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(colors.onSurface)
        )
    }
}