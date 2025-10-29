package com.flux.store.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.flux.store.navigation.routes.ProductDetailsRoutes
import com.flux.store.ui.screens.productDetail.ProductDetailsScreen
import com.flux.store.ui.screens.productDetail.ProductExploreScreen
import com.flux.store.ui.screens.productDetail.ProductListScreen
import com.flux.store.viewmodel.ProductListViewModel

fun NavGraphBuilder.productDetailsNavigation(
    navigateWithPayload: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    navController: NavHostController
) {
    composable(route = ProductDetailsRoutes.ProductListScreen.toRoute()) {
        val viewModel: ProductListViewModel = hiltViewModel()
        ProductListScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            navController = navController ,
        )
    }
    composable(route = ProductDetailsRoutes.ProductExploreScreen.toRoute()) {
        val viewModel: ProductListViewModel = hiltViewModel()
        ProductExploreScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            navController = navController ,
        )
    }
    composable(route = ProductDetailsRoutes.ProductDetailScreen.toRoute()) {
        val viewModel: ProductListViewModel = hiltViewModel()
        ProductDetailsScreen(
            viewModel = viewModel,
            onNavigate = navigateWithPayload,
            navController = navController ,
        )
    }
}