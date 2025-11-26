package com.example.unichargeandroid.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 💚 YOUR APP’S BRAND COLORS
val UniGreen = Color(0xFF18C77B)      // Primary green
val UniGreenDark = Color(0xFF0E9A5D)  // Darker green for dark theme
val TextBlack = Color(0xFF000000)
val TextWhite = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFE7F8F0)
val DarkSurface = Color(0xFF35383F)

private val LightColorScheme = lightColorScheme(
    primary = UniGreen,
    onPrimary = TextWhite,

    background = Color.White,
    onBackground = TextBlack,

    surface = LightSurface,
    onSurface = TextBlack,

    outlineVariant = Color(0xFFE0E0E0)
)

private val DarkColorScheme = darkColorScheme(
    primary = UniGreenDark,
    onPrimary = TextWhite,

    background = Color(0xFF191A1F),
    onBackground = TextWhite,

    surface = DarkSurface,
    onSurface = TextWhite,

    outlineVariant = Color(0xFF2D2D2D)
)

@Composable
fun UniChargeAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}