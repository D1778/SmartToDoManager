package com.example.smarttodomanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLightColor,
    onPrimary = Color.Black,
    secondary = SecondaryLightColor,
    onSecondary = Color.Black,
    tertiary = SecondaryColor,
    onTertiary = Color.Black,
    background = Color(0xFF12030B),
    onBackground = Color.Black,
    surface = Color(0xFF210514),
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black.copy(alpha = 0.7f),
    outline = Color.Black,
    primaryContainer = PrimaryDarkColor,
    onPrimaryContainer = Color.White,
    secondaryContainer = Color(0x33E91E63),
    onSecondaryContainer = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    secondary = SecondaryColor,
    onSecondary = Color.White,
    tertiary = SecondaryLightColor,
    onTertiary = Color.White,
    background = Color(0xFFC27BA0),
    onBackground = Color.Black,
    surface = Color(0xFFD19CB9),
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black.copy(alpha = 0.7f),
    outline = Color.Black,
    primaryContainer = Color(0xFFE1BEE7),
    onPrimaryContainer = Color.Black,
    secondaryContainer = Color(0xFFF8BBD0),
    onSecondaryContainer = Color.Black
)

@Composable
fun SmartToDoManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
