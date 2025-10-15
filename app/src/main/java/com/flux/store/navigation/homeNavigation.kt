package com.flux.store.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.ui.screens.home.CartScreen
import com.flux.store.ui.screens.home.ExploreScreen
import com.flux.store.ui.screens.home.HomeScreen
import com.flux.store.ui.screens.home.ProfileScreen
import com.flux.store.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeNavigation(
    navigateWithPayload: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    navController: NavHostController,
    drawerState: DrawerState
) {
    composable(route = HomeRoutes.HomeScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()

        HomeScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            navController= navController,
            drawerState = drawerState,
            onMenuClick = { scope.launch { drawerState.open() } }
        )
    }
    composable(route = HomeRoutes.ExploreScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
        ExploreScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            navController= navController,
            drawerState = drawerState,
            onMenuClick = { scope.launch { drawerState.open() } }
        )
    }
    composable(route = HomeRoutes.CartScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
        CartScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload,
            drawerState = drawerState,
            onMenuClick = { scope.launch { drawerState.open() } }
        )
    }
    composable(route = HomeRoutes.ProfileScreen.toRoute()) {
        val viewModel: HomeViewModel = hiltViewModel()
        val scope = rememberCoroutineScope()
        ProfileScreen(
            viewModel = viewModel,
            onBack = { navController.popBackStack() },
            onNavigate = navigateWithPayload,
            drawerState = drawerState,
            onMenuClick = { scope.launch { drawerState.open() } }
        )
    }
}