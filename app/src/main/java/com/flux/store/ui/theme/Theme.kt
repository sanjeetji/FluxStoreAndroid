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


private val LightColorScheme = lightColorScheme(
    primary =                    LightThemePrimaryColor,                    //0xFFFFFFFF
    onPrimary =                  LightThemeOnPrimaryColor,                //0xFF000000
    secondary =                  LightThemeSecondaryColor,                //0xFFFAFAFA
    onSecondary =                LightThemeOnSecondaryColor,            //0xFF121420
    tertiary =                   LightThemeTertiaryColor,                  //0xFF575757
    onTertiary =                 LightThemeOnTertiaryColor,              //0xFFFFFFFF
    background =                 LightThemeLightBackgroundColor,         //0xFFE6E8EC
    onBackground =               LightThemeLightOnBackgroundColor,     //0xFF000000
    surface =                    LightThemeLightSurfaceColor,               //0xFFFFFBF8
    onSurface =                  LightThemeLightOnSurfaceColor,           //0xFFFCFCFD
    error =                      LightThemeLightErrorColor,                   //0xFFF08C7D
    onError =                    LightThemeLightOnErrorColor,               //0xFFFFFFFF
    outline =                    LightThemeOutlineColor,                    //0xFFF2F2F2
    outlineVariant =             LightThemeOnOutlineColor,           //0xFF33302E
    surfaceVariant =             LightThemeIntroBackgroundColor,     //0xFF464447
    onSurfaceVariant =           LightThemeBottomBarUnselectedColor,//0xFFBEBFC4
    surfaceBright =              LightThemeSplashButtonColor,          //0xFF7a7a7a
)

private val DarkColorScheme = darkColorScheme(
    primary =                                                                               DarkThemePrimaryColor,                        //0xFF141416
    onPrimary =                  DarkThemeOnPrimaryColor,                    //0xFFFCFCFD
    secondary =                  DarkThemeSecondaryColor,                    //0xFF23262F
    onSecondary =                DarkThemeOnSecondaryColor,                //0xFFFFFFFF
    tertiary =                   DarkThemeTertiaryColor,                      //0xFFE6E8EC
    onTertiary =                 DarkThemeOnTertiaryColor,                  //0xFF23262F
    background =                 DarkThemeLightBackgroundColor,             //0xFF353945
    onBackground =               DarkThemeLightOnBackgroundColor,         //0xFFFFFFFF
    surface =                    DarkThemeLightSurfaceColor,                   //0xFFFFFBF8
    onSurface =                  DarkThemeLightOnSurfaceColor,               //0xFFFCFCFD
    error =                      DarkThemeLightErrorColor,                       //0xFFF08C7D
    onError =                    DarkThemeLightOnErrorColor,                   //0xFFFFFFFF
    outline =                    DarkThemeOutlineColor,                        //0xFF151515
    outlineVariant =             DarkThemeOnOutlineColor,               //0xFFB1B5C3
    surfaceVariant =             DarkThemeIntroBackgroundColor,         //0xFF464447
    onSurfaceVariant =           DarkThemeBottomBarUnselectedColor,   //0xFF353945
    surfaceBright =              DarkThemeSplashButtonColor,             //0xFF7a7a7a

)

@Composable
fun ComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,// keep off – we use custom palette
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

    // ---- System bar icon colors (light/dark) ----
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)

            // Light icons on dark background → false, dark icons on light → true
            val lightIcons = !darkTheme
            insetsController.isAppearanceLightStatusBars = lightIcons
            insetsController.isAppearanceLightNavigationBars = lightIcons
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}