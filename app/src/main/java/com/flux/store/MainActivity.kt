package com.flux.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
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
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb()),
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeAppTheme {
                val strings by locMgr.strings.collectAsState()
                CompositionLocalProvider(
                    LocalStrings provides strings,
                    LocalLocalizationManager provides locMgr
                ) {
                    val navController = rememberNavController()
                    val shouldUseBottomNav = false // Set to false to use no bottom navigation
                    AppNavigation(
                        navController = navController,
                        shouldShowBottomBar = shouldUseBottomNav
                    )
                }
            }
        }
    }
}
