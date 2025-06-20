package com.flux.store.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flux.store.model.response.MyData
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.navigation.routes.SplashRoutes
import com.flux.store.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    setBottomBarVisible: (Boolean) -> Unit
) {

    LaunchedEffect(Unit) {
        val shouldShow = false // or true
        setBottomBarVisible(shouldShow)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Count: ${viewModel.getValue()}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.incrementCount()
            viewModel.saveValue(viewModel.count)
        }) {
            Text("Increment Count")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val userId = 22
            val message = "This is example of payload."
            onNavigate(HomeRoutes.ProfileScreen.toRoute(), message, null, false)
        }) {
            Text("Go to Profile")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val myObject = MyData(name = "Apples", age = 5)
            onNavigate(HomeRoutes.CategoryScreen.toRoute(), myObject, null, false)
        }
        ) {
            Text("Go to Category")
        }
        Button(onClick = {
            onNavigate(SplashRoutes.IntroScreen.toRoute(), "myObject", null, false)
        }
        ) {
            Text("Go To Intro")
        }
    }
}