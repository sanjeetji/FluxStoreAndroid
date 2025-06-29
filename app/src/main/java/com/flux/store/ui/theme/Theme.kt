package com.flux.store.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary, // #343434
    onPrimary = DarkOnPrimary, // #FFFFFF
    secondary = DarkSecondary, // #464447
    onSecondary = DarkOnSecondary, // #FFFFFF
    tertiary = DarkTertiary, // #508A7B
    onTertiary = DarkOnTertiary, // #FFFFFF
    background = DarkBackground, // #121212
    onBackground = DarkOnBackground, // #E6E6E6
    surface = DarkSurface, // #1E1E1E
    onSurface = DarkOnSurface, // #E6E6E6
    error = DarkError, // #EE6969
    onError = DarkOnError, // #000000
    outline = DarkOutline // #6B6B6B
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary, // #343434
    onPrimary = LightOnPrimary, // #FFFFFF
    secondary = LightSecondary, // #464447
    onSecondary = LightOnSecondary, // #FFFFFF
    tertiary = LightTertiary, // #508A7B
    onTertiary = LightOnTertiary, // #FFFFFF
    background = LightBackground, // #F8F8F8
    onBackground = LightOnBackground, // #000000
    surface = LightSurface, // #FFFFFF
    onSurface = LightOnSurface, // #000000
    error = LightError, // #EE6969
    onError = LightOnError, // #FFFFFF
    outline = LightOutline // #8A8A8A
)

@Composable
fun ComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,// Disable dynamic colors to use custom Color.kt
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set transparent system bars (defined in theme, but ensure compatibility)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val insetsController = WindowCompat.getInsetsController(window, view)
            // Set light/dark appearance for status and navigation bars
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}