package com.flux.store.navigation

import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.ui.screens.home.CartScreen
import com.flux.store.ui.screens.home.CategoryScreen
import com.flux.store.ui.screens.home.HomeScreen
import com.flux.store.ui.screens.home.ProfileScreen
import com.flux.store.viewmodel.HomeViewModel

fun NavGraphBuilder.homeNavigation(
    navigateWithPayload: (String, Any?) -> Unit,
    navController: NavHostController,
    bottomBarVisible: MutableState<Boolean>
) {
    composable(route = HomeRoutes.HomeScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload,
            setBottomBarVisible = { visible -> bottomBarVisible.value = visible }
        )
    }
    composable(route = HomeRoutes.CategoryScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        CategoryScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload
        )
    }
    composable(route = HomeRoutes.CartScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        CartScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload
        )
    }
    composable(route = HomeRoutes.ProfileScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        ProfileScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload
        )
    }
}