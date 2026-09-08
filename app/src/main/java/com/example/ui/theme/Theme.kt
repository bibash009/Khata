package com.example.ui.theme

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
    primary = Color(0xFF818CF8), // Soft Indigo 400
    secondary = Color(0xFF6366F1), // Indigo 500
    tertiary = Color(0xFF38BDF8), // Sky 400
    background = Color(0xFF0B0F19), // Deep rich minimalist dark slate
    surface = Color(0xFF1E293B), // White-ish slate card surface
    surfaceVariant = Color(0xFF151F32), // Darker contrast surface
    onPrimary = Color(0xFF000000),
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color(0xFFF8FAFC), // Slate 50
    onSurface = Color(0xFFF1F5F9), // Slate 100
    onSurfaceVariant = Color(0xFF94A3B8) // Slate 400
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6366F1), // Clean Indigo primary
    secondary = Color(0xFF4F46E5), // Deep Indigo secondary
    tertiary = Color(0xFF0EA5E9), // Clean Emerald / Sky
    background = Color(0xFFF3F5F7), // Elegant minimalist light slate bg (#F3F5F7 from prompt)
    surface = Color(0xFFFFFFFF), // Crisp pure white cards/sheets
    surfaceVariant = Color(0xFFF8FAFC), // Very soft slate outline / background contrast
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF0F172A), // Slate 900
    onSurface = Color(0xFF1E293B), // Slate 800
    onSurfaceVariant = Color(0xFF64748B) // Slate 500
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Allow dynamic color toggle
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
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
