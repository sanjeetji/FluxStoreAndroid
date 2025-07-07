package com.flux.store.ui.screens.onboard.forgotPassword

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.R
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.ui.theme.ThemeColor
import com.flux.store.viewmodel.ForgotPasswordViewmodel

@Composable
fun VerificationCodeScreen(
    viewModel: ForgotPasswordViewmodel,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    onBack: () -> Unit,
    otpLength: Int = 4 // Configurable OTP length (4 or 6)
) {

    val context = LocalContext.current
    var otp by remember(otpLength) { mutableStateOf("") } // Reset OTP when otpLength changes
    var isError by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }

    // Handle toast display outside composable context
    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, (R.string.invalid_otp), Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    // Validate OTP when complete
    LaunchedEffect(otp, otpLength) {
        if (otp.length == otpLength) {
            if (viewModel.verifyOtp(otp)) {
                onNavigate(LoginRoutes.CreateNewPasswordScreen.toRoute(), "", null, false)
            } else {
                isError = true
                showToast = true
            }
        } else {
            isError = false // Reset error when OTP is incomplete
        }
    }

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
                .shadow(1.dp, shape = RoundedCornerShape(50.dp),clip = true)
                .clickable{
                onBack()
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = tr(R.string.verification_code),
            style = MaterialTheme.typography.headlineMedium,
            color = Black,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = tr(R.string.verification_code_description),
            style = MaterialTheme.typography.bodyMedium,
            color = Black,        )
        Spacer(modifier = Modifier.height(172.dp))

        // OTP Input Field
        OTPInputField(
            otp = otp,
            otpLength = otpLength,
            isError = isError,
            onOtpChange = { newOtp ->
                otp = newOtp.take(otpLength) // Limit input to otpLength
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (otp.length == otpLength) {
                    if (viewModel.verifyOtp(otp)) {
                        onNavigate(LoginRoutes.CreateNewPasswordScreen.toRoute(), "", null, false)
                    } else {
                        isError = true
                        showToast = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .semantics { contentDescription = "Submit verification button" },
            colors = ButtonDefaults.buttonColors(
                containerColor = ThemeColor,
                contentColor = White
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = otp.length == otpLength
        ) {
            Text(
                tr(R.string.confirm),
                style = MaterialTheme.typography.titleMedium,
                color = White)
        }
    }
}

@Composable
fun OTPInputField(
    otp: String,
    otpLength: Int,
    isError: Boolean,
    onOtpChange: (String) -> Unit
) {
    val focusRequesters = List(otpLength) { remember(otpLength) { FocusRequester() } }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(otpLength) { index ->
            val char = if (index < otp.length) otp[index].toString() else ""
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isError) Color(0xFFFFE6E6) // Light red for error
                        else Color(0xFFF5F5F5) // Light gray background
                    ),
                contentAlignment = Alignment.Center // Ensure content is centered
            ) {
                BasicTextField(
                    value = char,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                            val newOtp = buildString {
                                append(otp.take(index))
                                append(newValue)
                                append(otp.drop(index + 1))
                            }.take(otpLength)
                            onOtpChange(newOtp)
                            // Move focus to next field if digit entered
                            if (newValue.isNotEmpty() && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                            // Move focus to previous field if deleted
                            if (newValue.isEmpty() && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .padding(top = 8.dp)
                        .focusRequester(focusRequesters[index])
                        .semantics { contentDescription = "OTP digit ${index + 1}" },
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        },
                        onPrevious = {
                            if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        }
                    ),
                    singleLine = true
                )
            }
            if (index < otpLength - 1) Spacer(modifier = Modifier.width(8.dp))
        }
    }

    // Auto-focus first field on composition or otpLength change
    LaunchedEffect(otpLength) {
        focusRequesters[0].requestFocus()
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun VerificationCodeScreenPreview() {
    ComposeAppTheme {
        VerificationCodeScreen(
            viewModel = ForgotPasswordViewmodel(),
            onNavigate = { _, _, _, _ -> },
            onBack = {},
            otpLength = 4
        )
    }
}