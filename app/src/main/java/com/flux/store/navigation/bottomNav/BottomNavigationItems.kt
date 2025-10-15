package com.flux.store.navigation.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import com.flux.store.model.dto.BottomNavigation
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.helper.Constants

object BottomNavigationItems {
    val bottomNavigation = listOf(
        BottomNavigation(
            label = Constants.HOME_SCREEN,
            icon = Icons.Rounded.Home,
            route = HomeRoutes.HomeScreen
        ),
        BottomNavigation(
            label = Constants.EXPLORE_SCREEN,
            icon = Icons.Rounded.Search,
            route = HomeRoutes.ExploreScreen
        ),
        BottomNavigation(
            label = Constants.CART_SCREEN,
            icon = Icons.Rounded.ShoppingCart,
            route = HomeRoutes.CartScreen
        ),
        BottomNavigation(
            label = Constants.PROFILE_SCREEN,
            icon = Icons.Rounded.Person,
            route = HomeRoutes.ProfileScreen
        ),
    )
}