package com.flux.store.ui.screens.onboard.forgotPassword

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.R
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.ui.theme.Gray
import com.flux.store.ui.theme.LightGray1
import com.flux.store.viewmodel.ForgotPasswordViewmodel

@Composable
fun CreateNewPasswordScreen(
    viewModel: ForgotPasswordViewmodel,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    onBack: () -> Unit
) {

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .statusBarsPadding()
        .padding(start = 22.dp, end = 32.dp)) {
       // Spacer(modifier = Modifier.height(18.dp))
        Image(painterResource(R.drawable.ic_back), contentDescription = "back button",
            modifier = Modifier.clickable{onBack()})
        Spacer(modifier = Modifier.height(24.dp))
        Text(tr(R.string.create_new_password), style = TextStyle(fontSize = 24.sp))
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            tr(R.string.create_new_password_description),
            style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Default)
        )

        Spacer(modifier = Modifier.height(64.dp))

        TextField(
            value = password,
            onValueChange = { newValue -> password = newValue },
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = {
                Text(
                    tr(R.string.hint_enter_your_password),
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)))
                .semantics { contentDescription = "Name input field" },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,  // Replace with your custom icon or drawable
                    contentDescription = "Person Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Gray,  // Replace with your focused color
                unfocusedContainerColor = LightGray1,  // Replace with your unfocused color
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedIndicatorColor = Color.Gray,  // Customize indicator color when focused
                unfocusedLabelColor = Gray,  // Customize label color when unfocused
                focusedLabelColor = Color.White,  // Customize label color when focused
                unfocusedIndicatorColor = LightGray1
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { newValue -> confirmPassword = newValue },
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = {
                Text(
                    tr(R.string.hint_enter_your_password),
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)))
                .semantics { contentDescription = "Name input field" },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,  // Replace with your custom icon or drawable
                    contentDescription = "Person Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Gray,  // Replace with your focused color
                unfocusedContainerColor = LightGray1,  // Replace with your unfocused color
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedIndicatorColor = Color.Gray,  // Customize indicator color when focused
                unfocusedLabelColor = Gray,  // Customize label color when unfocused
                focusedLabelColor = Color.White,  // Customize label color when focused
                unfocusedIndicatorColor = LightGray1
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(108.dp))

        Button(
            onClick = {
                handleConfirmPasswordBtn(context, onNavigate, viewModel, password, confirmPassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .semantics { contentDescription = "Submit registration button" },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // #464447
                contentColor = MaterialTheme.colorScheme.onPrimary // #FFFFFF
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(tr(R.string.confirm_password))
        }
    }
}

private fun handleConfirmPasswordBtn(context: Context, onNavigate: (String, Any?, String?, Boolean) -> Unit, viewModel: ForgotPasswordViewmodel, password: String, confirmPassword: String){
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
    Toast.makeText(context, "Password is changed successfully...", Toast.LENGTH_SHORT).show()
    onNavigate(HomeRoutes.HomeScreen.toRoute(), "", null, false)
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CreateNewPasswordScreenPreview() {
    ComposeAppTheme {
        CreateNewPasswordScreen(
            viewModel = ForgotPasswordViewmodel(),
            onNavigate = { route, _, _, _ -> },
            onBack = {}
        )
    }
}