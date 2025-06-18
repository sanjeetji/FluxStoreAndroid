package com.flux.store.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.screens.loginRegistration.LoginScreen
import com.flux.store.ui.screens.loginRegistration.RegistrationScreen
import com.flux.store.viewmodel.LoginRegistrationViewmodel

fun NavGraphBuilder.loginNavigation(
    navigateWithPayload: (String, Any?) -> Unit,
    navController: NavHostController
) {
    composable(route = LoginRoutes.RegistrationScreen.toRoute()) {
        val viewModel: LoginRegistrationViewmodel = hiltViewModel()
        RegistrationScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            onBack = { navController.popBackStack() }
        )
    }
    composable(route = LoginRoutes.LoginScreen.toRoute()) {
        val viewModel: LoginRegistrationViewmodel = hiltViewModel()
        LoginScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            onBack = { navController.popBackStack() }
        )
    }
}