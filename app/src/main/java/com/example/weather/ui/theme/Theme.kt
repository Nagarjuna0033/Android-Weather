package com.example.weather.ui.theme

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


private val DarkColorScheme = darkColorScheme(

    primary = Color(0xFF252523), // Primary Color for Background
    secondary = Color(0xFF5A5959), // secondary color for border of text fields
    onBackground = Color.White, // color for text
    primaryContainer = Color(0xFF000000), // color for background of textFields
    onError = Color.Red // color for error

)

private val LightColorScheme = lightColorScheme(

    primary = Color(0xFFF1EDED), // Primary Color for Background
    secondary = Color(0xFF54626F),// secondary color for border of text fields
    onBackground = Color.Black, // color for text
    primaryContainer = Color(0xFFF5F5F5),  // color for background of textFields
    onError = Color.Red // color for error

)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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