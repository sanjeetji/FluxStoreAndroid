package com.flux.store.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.flux.store.navigation.routes.SplashRoutes
import com.flux.store.ui.screens.splash.IntroScreen
import com.flux.store.ui.screens.splash.SplashScreen
import com.flux.store.viewmodel.IntroViewmodel
import com.flux.store.viewmodel.SplashViewModel

fun NavGraphBuilder.splashNavigation(
    navigateWithPayload: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    navController: NavHostController
) {
    composable(route = SplashRoutes.SplashScreen.toRoute()) {
        val viewModel: SplashViewModel = hiltViewModel()
        SplashScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload
        )
    }

    composable(route = SplashRoutes.IntroScreen.toRoute()) {
        val viewModel: IntroViewmodel = hiltViewModel()
        IntroScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload
        )
    }
}