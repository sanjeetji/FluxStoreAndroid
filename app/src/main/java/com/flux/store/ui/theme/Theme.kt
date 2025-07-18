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
    primary = DarkThemePrimaryColor,                        //0xFF141416
    onPrimary = DarkThemeOnPrimaryColor,                    //0xFFFCFCFD
    secondary = DarkThemeSecondaryColor,                    //0xFF23262F
    onSecondary = DarkThemeOnSecondaryColor,                //0xFFFFFFFF
    tertiary = DarkThemeTertiaryColor,                      //0xFFE6E8EC
    onTertiary = DarkThemeOnTertiaryColor,                  //0xFF23262F
    background = DarkThemeLightBackgroundColor,             //0xFF353945
    onBackground = DarkThemeLightOnBackgroundColor,         //0xFFFFFFFF
    surface = DarkThemeLightSurfaceColor,                   //0xFFFFFBF8
    onSurface = DarkThemeLightOnSurfaceColor,               //0xFFFCFCFD
    error = DarkThemeLightErrorColor,                       //0xFFF08C7D
    onError = DarkThemeLightOnErrorColor,                   //0xFFFFFFFF
    outline = DarkThemeOutlineColor,                        //0xFF151515
    outlineVariant = DarkThemeOnOutlineColor,               //0xFFB1B5C3
    surfaceVariant = DarkThemeIntroBackgroundColor,         //0xFF464447
    onSurfaceVariant = DarkThemeBottomBarUnselectedColor,   //0xFF353945
    surfaceBright = DarkThemeSplashButtonColor,             //0xFF7a7a7a

)

private val JetBlackLightColorScheme = lightColorScheme(
    primary = JetBlackThemePrimaryColor,                    //0xFFFFFFFF
    onPrimary = JetBlackThemeOnPrimaryColor,                //0xFF000000
    secondary = JetBlackThemeSecondaryColor,                //0xFFFAFAFA
    onSecondary = JetBlackThemeOnSecondaryColor,            //0xFF121420
    tertiary = JetBlackThemeTertiaryColor,                  //0xFF575757
    onTertiary = JetBlackThemeOnTertiaryColor,              //0xFFFFFFFF
    background = JetBlackThemeLightBackgroundColor,         //0xFFE6E8EC
    onBackground = JetBlackThemeLightOnBackgroundColor,     //0xFF000000
    surface = JetBlackThemeLightSurfaceColor,               //0xFFFFFBF8
    onSurface = JetBlackThemeLightOnSurfaceColor,           //0xFFFCFCFD
    error = JetBlackThemeLightErrorColor,                   //0xFFF08C7D
    onError = JetBlackThemeLightOnErrorColor,               //0xFFFFFFFF
    outline = JetBlackThemeOutlineColor,                    //0xFFF2F2F2
    outlineVariant = JetBlackThemeOnOutlineColor,           //0xFF33302E
    surfaceVariant = JetBlackThemeIntroBackgroundColor,     //0xFF464447
    onSurfaceVariant = JetBlackThemeBottomBarUnselectedColor,//0xFFBEBFC4
    surfaceBright = JetBlackThemeSplashButtonColor,          //0xFF7a7a7a
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
        else -> JetBlackLightColorScheme
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