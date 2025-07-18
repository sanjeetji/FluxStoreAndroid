package com.flux.store.ui.screens.onboard.forgotPassword

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.flux.store.R
import com.flux.store.helper.BottomSheetHelper
import com.flux.store.helper.ComposeFileHelper
import com.flux.store.helper.clearAndNavigateTo
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.viewmodel.ForgotPasswordViewmodel

@Composable
fun CreateNewPasswordScreen(
    viewModel: ForgotPasswordViewmodel,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(start = 22.dp, end = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painterResource(R.drawable.ic_back),
                contentDescription = "back button",
                modifier = Modifier
                    .shadow(1.dp, shape = RoundedCornerShape(50.dp), clip = true)
                    .clickable {
                        navController.popBackStack()
                    })
            Spacer(modifier = Modifier.height(24.dp))
            Text(tr(R.string.create_new_password),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                tr(R.string.create_new_password_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.height(64.dp))
            TextField(
                value = password,
                onValueChange = { newValue ->
                    password = newValue
                },
                textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Start),
                placeholder = {
                    Text(
                        tr(R.string.new_password),
                        style = TextStyle(fontSize = 14.sp, textAlign = TextAlign.Start),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(0.1.dp, MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
                    .semantics { contentDescription = "Password input field" },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Lock Icon",
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = ComposeFileHelper.defaultTextFieldColors(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }))
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = confirmPassword,
                onValueChange = { newValue ->
                    confirmPassword = newValue
                },
                textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Start),
                placeholder = {
                    Text(
                        tr(R.string.hint_enter_your_password),
                        style = TextStyle(fontSize = 14.sp, textAlign = TextAlign.Start),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(0.1.dp, MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
                    .semantics { contentDescription = "Confirm password input field" },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Lock Icon",
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = ComposeFileHelper.defaultTextFieldColors(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }))
            Spacer(modifier = Modifier.height(108.dp))
            Button(
                onClick = {
                handleConfirmPasswordBtn(
                    context,
                    viewModel,
                    password,
                    confirmPassword,
                    onShowSheet = { showBottomSheet = true },
                )
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .semantics { contentDescription = "Submit registration button" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    tr(R.string.confirm_password),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        // --- show the sheet when the flag is true ---
        if (showBottomSheet) {
            HandleBottomSheet(
                onDismiss = { showBottomSheet = false },
                onNavigate = onNavigate,
                navController = navController
            )
        }
    }

}

@Composable
private fun HandleBottomSheet(
    onDismiss: () -> Unit,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController
) {
    BottomSheetHelper(
        onDismiss = onDismiss,
        title = tr(R.string.your_password_has_changed),
        description = tr(R.string.discover_now),
        buttonText = tr(R.string.browse_home),
        onButtonClick = {
            onDismiss()
            navController.clearAndNavigateTo(HomeRoutes.HomeScreen)
//            onNavigate(HomeRoutes.HomeScreen.toRoute(), null, LoginRoutes.CreateNewPasswordScreen.toRoute(), true)
        })
}

private fun handleConfirmPasswordBtn(
    context: Context,
    viewModel: ForgotPasswordViewmodel,
    password: String,
    confirmPassword: String,
    onShowSheet: () -> Unit
) {
    if (password.isEmpty()) {
        Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show()
        return
    }
    if (confirmPassword.isEmpty()) {
        Toast.makeText(context, "Please enter confirm password", Toast.LENGTH_SHORT).show()
        return
    }
    if (password != confirmPassword) {
        Toast.makeText(context, "Please enter same password", Toast.LENGTH_SHORT).show()
        return
    }
    Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
    onShowSheet()
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CreateNewPasswordScreenPreview() {
    ComposeAppTheme {
        CreateNewPasswordScreen(
            viewModel = ForgotPasswordViewmodel(),
            onNavigate = { route, _, _, _ -> },
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetHelperPreview() {
    ComposeAppTheme {
        BottomSheetHelper(
            onDismiss = {},
            title = tr(R.string.your_password_has_changed),
            description = tr(R.string.discover_now),
            buttonText = tr(R.string.browse_home),
            onButtonClick = {})
    }
}