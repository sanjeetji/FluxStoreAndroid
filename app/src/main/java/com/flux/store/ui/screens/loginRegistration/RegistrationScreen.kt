package com.flux.store.ui.screens.loginRegistration

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.viewmodel.LoginRegistrationViewmodel

@Composable
fun RegistrationScreen(
    viewModel: LoginRegistrationViewmodel,
    onNavigate: (route: String, payload: Any?) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") } // State for name input

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) // #F8F8F8 (light) or #121212 (dark)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create your account",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onBackground, // #000000 (light) or #E6E6E6 (dark)
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = name,
            onValueChange = { newValue -> name = newValue },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Name input field" },
            singleLine = true,
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface, // #FFFFFF (light) or #1C2526 (dark)
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLabelColor = MaterialTheme.colorScheme.primary, // #343434
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant // #49454F (light) or #CAC4D0 (dark)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    onNavigate(HomeRoutes.HomeScreen.toRoute(), null) // Navigate to HomeScreen
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .semantics { contentDescription = "Submit registration button" },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary, // #464447
                contentColor = MaterialTheme.colorScheme.onSecondary // #FFFFFF
            )
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onBack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .semantics { contentDescription = "Back button" },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // #343434
                contentColor = MaterialTheme.colorScheme.onPrimary // #FFFFFF
            )
        ) {
            Text("Back")
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    ComposeAppTheme {
        RegistrationScreen(
            viewModel = LoginRegistrationViewmodel(),
            onNavigate = { _, _ -> },
            onBack = {}
        )
    }
}