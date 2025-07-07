package com.flux.store.ui.screens.onboard.forgotPassword

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.R
import com.flux.store.helper.Helper
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.theme.BlackColor
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.ui.theme.LightGrayColor
import com.flux.store.ui.theme.LightWhiteColor
import com.flux.store.ui.theme.ThemeColor
import com.flux.store.viewmodel.ForgotPasswordViewmodel

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewmodel,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    onBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 22.dp, end = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "back button",
            modifier = Modifier
                .shadow(1.dp, shape = RoundedCornerShape(50.dp), clip = true)
                .clickable {
                    onBack()
                }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(tr(R.string.forgot_password),
                style = MaterialTheme.typography.headlineMedium,
                color = Black,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                tr(R.string.forgot_password_description),
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
            )
            Spacer(modifier = Modifier.height(120.dp))
            TextField(
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                },
                placeholder = {
                    Text(
                        tr(R.string.hint_enter_your_email),
                        style = MaterialTheme.typography.bodySmall,
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(0.1.dp, BlackColor, shape = RoundedCornerShape(10.dp))
                    .semantics { contentDescription = "Name input field" },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Person Icon",
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LightGrayColor,
                    unfocusedContainerColor = LightWhiteColor,
                    focusedTextColor = White,
                    unfocusedTextColor = BlackColor,
                    focusedPlaceholderColor = ThemeColor,
                    unfocusedPlaceholderColor = LightGrayColor,
                    focusedIndicatorColor = LightGrayColor,
                    unfocusedIndicatorColor = White,
                    unfocusedLeadingIconColor = BlackColor,
                    focusedLeadingIconColor = White
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    handleConfirmBtn(context, onNavigate, viewModel, email)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .semantics { contentDescription = "Submit registration button" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ThemeColor,
                    contentColor = White
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    tr(R.string.login),
                    style = MaterialTheme.typography.titleMedium,
                    color = White,
                )
            }
        }
    }
}

fun handleConfirmBtn(
    context: Context,
    onNavigate: (String, Any?, String?, Boolean) -> Unit,
    viewModel: ForgotPasswordViewmodel,
    email: String
) {
    if (email.isEmpty() || !Helper.isValidEmail(email)) {
        Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show()
        return
    }
    Toast.makeText(context, "Password reset link sent to your email: $email.", Toast.LENGTH_SHORT).show()
    onNavigate(LoginRoutes.VerificationCodeScreen.toRoute(), "", null, false)
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ComposeAppTheme {
        ForgotPasswordScreen(
            viewModel = ForgotPasswordViewmodel(),
            onNavigate = { route, _, _, _ -> },
            onBack = {}
        )
    }
}