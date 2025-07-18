package com.flux.store.ui.screens.onboard

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.flux.store.R
import com.flux.store.helper.ComposeFileHelper
import com.flux.store.helper.Helper
import com.flux.store.helper.clearAndNavigateTo
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.navigation.routes.HomeRoutes
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.viewmodel.LoginRegistrationViewmodel

@Composable
fun LoginScreen(
    viewModel: LoginRegistrationViewmodel,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()


    BackHandler {
        navController.popBackStack()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.surface) // #F8F8F8 (light) or #121212 (dark)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(72.dp))
        Text(
            text = tr(R.string.login_into_your_account),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(52.dp))

        TextField(
            value = email,
            onValueChange = { newValue -> email = newValue },
            placeholder = {
                Text(
                    tr(R.string.hint_enter_your_email),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .border(0.1.dp, MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
                .semantics { contentDescription = "Name input field" },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,  // Replace with your custom icon or drawable
                    contentDescription = "Person Icon",
                )
            },
            colors = ComposeFileHelper.defaultTextFieldColors(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = password,
            onValueChange = { newValue -> password = newValue },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    tr(R.string.hint_enter_your_password),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .border(0.1.dp, MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
                .semantics { contentDescription = "Name input field" },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,  // Replace with your custom icon or drawable
                    contentDescription = "Person Icon",
                )
            },
            colors = ComposeFileHelper.defaultTextFieldColors(),
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

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onNavigate(LoginRoutes.ForgotPasswordScreen.toRoute(), "", null, false)
                }
                .wrapContentSize(),
            text = tr(R.string.forgot_password),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                handleLoginBtn(navController, context, onNavigate, viewModel, email, password)
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
                tr(R.string.login),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = tr(R.string.or_login_with),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(22.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 52.dp, end = 52.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                painter = painterResource(R.drawable.ic_apple),
                contentDescription = "apple logo"
            )
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                painter = painterResource(R.drawable.ic_google),
                contentDescription = "apple logo"
            )
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                painter = painterResource(R.drawable.ic_facebook),
                contentDescription = "apple logo"
            )
        }

        Spacer(modifier = Modifier.height(108.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = tr(R.string.do_not_have_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = tr(R.string.sing_up),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.clickable {
                    onNavigate(
                        LoginRoutes.RegistrationScreen.toRoute(),
                        "",
                        LoginRoutes.LoginScreen.toRoute(),
                        true
                    )
                }
            )
        }
    }
}

fun handleLoginBtn(
    navController: NavHostController,
    context: Context,
    onNavigate: (String, Any?, String?, Boolean) -> Unit,
    viewModel: LoginRegistrationViewmodel,
    email: String,
    password: String
) {
    if (email.isEmpty() || !Helper.isValidEmail(email)) {
        Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show()
        return
    }
    if (password.isEmpty()) {
        Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show()
        return
    }
    Toast.makeText(context, "User is logged in successful...", Toast.LENGTH_SHORT).show()
    viewModel.loginUser(email, password)
    navController.clearAndNavigateTo(HomeRoutes.HomeScreen)
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ComposeAppTheme {
        LoginScreen(
            viewModel = LoginRegistrationViewmodel(),
            onNavigate = { route, _, _, _ -> },
            navController = rememberNavController()
        )
    }
}