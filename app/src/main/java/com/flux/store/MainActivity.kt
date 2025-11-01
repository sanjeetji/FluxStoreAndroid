package com.flux.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.navigation.AppNavigation
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.helper.localizationHelper.LocalLocalizationManager
import com.flux.store.helper.localizationHelper.LocalStrings
import com.flux.store.helper.localizationHelper.LocalizationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var locMgr: LocalizationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ---- Transparent system bars (edge-to-edge) ----
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )
        // Allow drawing behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Use theme-controlled dark mode
            val isDarkTheme = rememberSaveable { mutableStateOf(false) }
            val bottomBarVisible = rememberSaveable { mutableStateOf(false) }

            CompositionLocalProvider(
                LocalIsDarkTheme provides isDarkTheme,
                LocalBottomBarVisible provides bottomBarVisible,
                LocalLocalizationManager provides locMgr,
                LocalStrings provides locMgr.strings.collectAsState().value
            ) {
                ComposeAppTheme(darkTheme = isDarkTheme.value) {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}
