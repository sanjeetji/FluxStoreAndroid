package com.flux.store.ui.screens.loginRegistration

import androidx.compose.runtime.Composable
import com.flux.store.viewmodel.LoginRegistrationViewmodel

@Composable
fun LoginScreen(
    viewModel: LoginRegistrationViewmodel,
    onBack: () -> Unit,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit
) {

}