package com.flux.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.flux.store.navigation.AppNavigation
import com.flux.store.ui.theme.ComposeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                val navController = rememberNavController()
                val shouldUseBottomNav = false // Set to false to use no bottom navigation
                AppNavigation(navController = navController, shouldShowBottomBar = shouldUseBottomNav)
            }
        }
    }
}
