package com.flux.store.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.screens.onboard.forgotPassword.CreateNewPasswordScreen
import com.flux.store.ui.screens.onboard.forgotPassword.ForgotPasswordScreen
import com.flux.store.ui.screens.onboard.forgotPassword.VerificationCodeScreen
import com.flux.store.ui.screens.localizaiton.LanguagePickerScreen
import com.flux.store.ui.screens.onboard.LoginScreen
import com.flux.store.ui.screens.onboard.RegistrationScreen
import com.flux.store.viewmodel.ForgotPasswordViewmodel
import com.flux.store.viewmodel.LoginRegistrationViewmodel

fun NavGraphBuilder.loginNavigation(
    navigateWithPayload: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
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

    composable(route = LoginRoutes.LanguagePickerScreen.toRoute()) {
        val viewModel: LoginRegistrationViewmodel = hiltViewModel()
        LanguagePickerScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            onBack = { navController.popBackStack() }
        )
    }


    composable(route = LoginRoutes.ForgotPasswordScreen.toRoute()) {
        val viewModel: ForgotPasswordViewmodel = hiltViewModel()
        ForgotPasswordScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            onBack = { navController.popBackStack() }
        )
    }

    composable(route = LoginRoutes.VerificationCodeScreen.toRoute()) {
        val viewModel: ForgotPasswordViewmodel = hiltViewModel()
        VerificationCodeScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            onBack = { navController.popBackStack() }
        )
    }

    composable(route = LoginRoutes.CreateNewPasswordScreen.toRoute()) {
        val viewModel: ForgotPasswordViewmodel = hiltViewModel()
        CreateNewPasswordScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            navController =  navController
        )
    }

}