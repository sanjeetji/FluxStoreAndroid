package com.flux.store.navigation.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import com.flux.store.model.response.BottomNavigation
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.utils.Constants

object BottomNavigationItems {
    val bottomNavigation = listOf(
        BottomNavigation(
            label = Constants.HOME_SCREEN,
            icon = Icons.Rounded.Home,
            route = HomeRoutes.HomeScreen
        ),
        BottomNavigation(
            label = Constants.CATEGORY_SCREEN,
            icon = Icons.Rounded.Lock,
            route = HomeRoutes.CategoryScreen
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