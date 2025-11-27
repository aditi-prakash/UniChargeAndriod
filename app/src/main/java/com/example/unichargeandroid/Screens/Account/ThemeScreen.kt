package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeScreen(
    onBackClick: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var selectedTheme by remember { mutableStateOf("Auto") } // Default selection

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 22.dp, end = 22.dp, bottom = 22.dp),
        verticalArrangement = Arrangement.Top
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
            Text("Theme", style = typography.titleLarge, color = colors.onBackground)
        }

        Spacer(Modifier.height(20.dp))

        // Options
        ThemeRowItem(
            text = "Auto",
            selected = selectedTheme == "Auto",
            onClick = { selectedTheme = "Auto" }
        )

        ThemeRowItem(
            text = "Light Theme",
            selected = selectedTheme == "Light Theme",
            onClick = { selectedTheme = "Light Theme" }
        )

        ThemeRowItem(
            text = "Dark Theme",
            selected = selectedTheme == "Dark Theme",
            onClick = { selectedTheme = "Dark Theme" }
        )
    }
}

@Composable
fun ThemeRowItem(text: String, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = typography.bodyLarge, color = colors.onSurface)

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = colors.primary
            )
        }
    }
}