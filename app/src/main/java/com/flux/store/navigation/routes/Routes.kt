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
    data object ExploreScreen : HomeRoutes {
        override fun toRoute() = "explore"
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

    @Serializable
    data object LanguagePickerScreen : LoginRoutes {
        override fun toRoute() = "languagePickerScreen"
    }

    @Serializable
    data object ForgotPasswordScreen : LoginRoutes {
        override fun toRoute() = "forgotPasswordScreen"
    }

    @Serializable
    data object VerificationCodeScreen : LoginRoutes {
        override fun toRoute() = "verificationCodeScreen"
    }

    @Serializable
    data object CreateNewPasswordScreen : LoginRoutes {
        override fun toRoute() = "createNewPasswordScreen"
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
