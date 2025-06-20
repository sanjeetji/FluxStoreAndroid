/*
package sanjeet.demo.composeapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    @Serializable data object SplashScreen : Routes
    @Serializable data object IntroScreen:Routes
    @Serializable data object RegistrationScreen:Routes
    @Serializable data object HomeScreen : Routes
    @Serializable data object CategoryScreen : Routes
    @Serializable data object CartScreen : Routes
    @Serializable data object ProfileScreen : Routes
}

fun Routes.toRoute(): String = when (this) {
    Routes.SplashScreen             -> "splash"
    Routes.IntroScreen              -> "intro"
    Routes.RegistrationScreen       -> "registration"
    Routes.HomeScreen               -> "home"
    Routes.CategoryScreen           -> "category"
    Routes.CartScreen               -> "cart"
    is Routes.ProfileScreen         -> "profile"
}*/

package com.flux.store.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    fun toRoute(): String
}

@Serializable
sealed interface HomeRoutes : Routes {
    @Serializable
    data object HomeScreen : HomeRoutes {
        override fun toRoute() = "home"
    }

    @Serializable
    data object CategoryScreen : HomeRoutes {
        override fun toRoute() = "category"
    }

    @Serializable
    data object CartScreen : HomeRoutes {
        override fun toRoute() = "cart"
    }

    @Serializable
    data object ProfileScreen : HomeRoutes {
        override fun toRoute() = "profile"
    }
}

@Serializable
sealed interface LoginRoutes : Routes {
    @Serializable
    data object RegistrationScreen : LoginRoutes {
        override fun toRoute() = "registration"
    }

    @Serializable
    data object LoginScreen : LoginRoutes {
        override fun toRoute() = "login"
    }
}


@Serializable
sealed interface SplashRoutes : Routes {
    @Serializable
    data object SplashScreen : SplashRoutes {
        override fun toRoute() = "splash"
    }

    @Serializable
    data object IntroScreen : SplashRoutes {
        override fun toRoute() = "intro"
    }
}
