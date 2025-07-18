package com.flux.store.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flux.store.R
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.navigation.routes.SplashRoutes
import com.flux.store.helper.AnimatedEntrance
import com.flux.store.helper.AppStateManager
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.helper.SlideDirection
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel, onBack: () -> Unit, onNavigate: (
        route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean
    ) -> Unit
) {

    val isDarkState = LocalIsDarkTheme.current
    isDarkState.value = false

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_img),
            contentDescription = "Logo",
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp)
            ) {
                AnimatedEntrance(SlideDirection.Top, 0) { modifier ->
                    Text(
                        text = tr(R.string.welcome_to_gem_store),
                        modifier = modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                AnimatedEntrance(SlideDirection.Left, 0) { modifier ->
                    Text(
                        modifier = modifier.align(Alignment.CenterHorizontally),
                        text = tr(R.string.the_home_for),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                AnimatedEntrance(SlideDirection.Bottom, 0) { modifier ->
                    Button(
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                            .width(200.dp)
                            .height(50.dp)
                            .border(2.dp, Color.White, CircleShape)
                            .semantics { contentDescription = "Navigate to next page or home" },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceBright
                        ),
                        onClick = { handleGetStarted(onNavigate, viewModel) }) {
                        Text(
                            text = tr(R.string.get_started),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                            )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(1000)
        val targetRoute = when {
            !viewModel.hasSeenIntro.value -> SplashRoutes.IntroScreen.toRoute()
            !viewModel.isUserLoggedIn.value -> LoginRoutes.RegistrationScreen.toRoute()
            else -> HomeRoutes.HomeScreen.toRoute()
        }
        onNavigate(targetRoute, null, SplashRoutes.SplashScreen.toRoute(), true)

    }

}

fun handleGetStarted(
    onNavigate: (String, Any?, String?, Boolean) -> Unit, viewModel: SplashViewModel
) {
    val targetRoute = when {
        !viewModel.hasSeenIntro.value -> SplashRoutes.IntroScreen.toRoute()
        !viewModel.isUserLoggedIn.value -> LoginRoutes.RegistrationScreen.toRoute()
        else -> HomeRoutes.HomeScreen.toRoute()
    }
    onNavigate(targetRoute, null, SplashRoutes.SplashScreen.toRoute(), true)
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun SplashScreenView() {

    ComposeAppTheme(false) {
        LocalBottomBarVisible provides remember { mutableStateOf(true) }
        LocalIsDarkTheme provides remember { mutableStateOf(false) }
        val context = LocalContext.current
        SplashScreen(
            viewModel = SplashViewModel(AppStateManager(context)), // This may require default constructor or mock
            onBack = {}, onNavigate = { route, _, _, _ -> })
    }
}

