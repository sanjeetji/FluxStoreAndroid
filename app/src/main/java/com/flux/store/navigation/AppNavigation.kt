package com.flux.store.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.flux.store.navigation.bottomNav.BottomBar
import com.flux.store.navigation.routes.SplashRoutes
import com.flux.store.helper.LocalBottomBarVisible


@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    val startRoute = SplashRoutes.SplashScreen.toRoute()
    val bottomBarVisible = LocalBottomBarVisible.current


    fun navigateWithPayload(
        route: String,
        payload: Any? = null,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
    ) {
        println("Navigating to $route with payload $payload")
        navController.currentBackStackEntry?.savedStateHandle?.set("navPayload", payload)

        navController.navigate(route) {
            popUpToRoute?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                }
            }
            launchSingleTop = true
        }
    }


    Scaffold(
        bottomBar = { if (bottomBarVisible.value) BottomBar(navController) },
        modifier = Modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            splashNavigation(::navigateWithPayload, navController)
            loginNavigation(::navigateWithPayload, navController)
            homeNavigation(::navigateWithPayload, navController)
        }
    }
}
