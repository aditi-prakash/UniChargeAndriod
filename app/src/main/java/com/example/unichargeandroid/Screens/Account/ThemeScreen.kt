package com.example.unichargeandroid.Screens.Account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeScreen() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    var selectedTheme by remember { mutableStateOf("Auto") } // Default selection

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp, start = 22.dp, end = 22.dp, bottom = 22.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // Top Bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = colors.onBackground, modifier = Modifier.size(26.dp))
            Spacer(Modifier.width(16.dp))
            Text("Theme", style = typography.titleLarge, color = colors.onBackground)
        }

        Spacer(Modifier.height(20.dp))

        // Suggested label
        Text(
            "Suggested",
            style = typography.bodyMedium,
            color = colors.onSurfaceVariant
        )

        Spacer(Modifier.height(16.dp))

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

        Spacer(Modifier.height(8.dp))
        Divider(color = colors.surfaceVariant, thickness = 1.dp)
    }
}

@Composable
fun ThemeRowItem(text: String, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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