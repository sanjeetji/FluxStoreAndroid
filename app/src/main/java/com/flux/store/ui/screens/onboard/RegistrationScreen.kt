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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.flux.store.helper.ComposeFileHelper
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.helper.Helper
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.viewmodel.LoginRegistrationViewmodel

@Composable
fun RegistrationScreen(
    viewModel: LoginRegistrationViewmodel,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isFocused = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()


    BackHandler {
        onBack()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                MaterialTheme.colorScheme.primary
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = tr(R.string.create_your_account),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(52.dp))

        TextField(
            value = name,
            onValueChange = { newValue -> name = newValue },
            placeholder = {
                Text(
                    tr(R.string.hint_enter_your_name),
                    style = MaterialTheme.typography.bodySmall,
                )
            }, // Hint text shown when TextField is empty
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .border(0.1.dp, MaterialTheme.colorScheme.outlineVariant, shape = RoundedCornerShape(10.dp))
                .semantics { contentDescription = "Name input field" }
                .focusRequester(focusRequester), // Attach FocusRequester
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,  // Replace with your custom icon or drawable
                    contentDescription = "Person Icon"
                )
            },
            colors = ComposeFileHelper.defaultTextFieldColors(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ), // Set IME action to Next
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = phone,
            onValueChange = { newValue -> phone = newValue },
            placeholder = {
                Text(
                    tr(R.string.hint_enter_your_phone_no),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .border(0.1.dp, MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
                .semantics { contentDescription = "Name input field" },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Phone,  // Replace with your custom icon or drawable
                    contentDescription = "Person Icon",
                )
            },
            colors = ComposeFileHelper.defaultTextFieldColors(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = email,
            onValueChange = { newValue -> email = newValue },
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
            textStyle = TextStyle(fontSize = 18.sp),
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

        Spacer(modifier = Modifier.height(75.dp))

        Button(
            onClick = {
                handleSignUpBtn(context, onNavigate, viewModel, name, email, phone, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .semantics { contentDescription = "Submit registration button" },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                tr(R.string.sing_up),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = tr(R.string.or_sign_up_with),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(32.dp))

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

        Spacer(modifier = Modifier.height(52.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = tr(R.string.already_have_an_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = tr(R.string.login),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
                , modifier = Modifier.clickable {
                    onNavigate(LoginRoutes.LoginScreen.toRoute(),"" , LoginRoutes.RegistrationScreen.toRoute(), true)
//                    onNavigate(LoginRoutes.LanguagePickerScreen.toRoute(), "", LoginRoutes.RegistrationScreen.toRoute(), true)
                }
            )
        }
    }
}

fun handleSignUpBtn(
    context: Context,
    onNavigate: (String, Any?, String?, Boolean) -> Unit,
    viewModel: LoginRegistrationViewmodel,
    name: String,
    email: String,
    phone: String,
    password: String
) {
    if (name.isEmpty()) {
        Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show()
        return
    }
    if (email.isEmpty() || !Helper.isValidEmail(email)) {
        Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show()
        return
    }
    if (phone.isEmpty() || !Helper.isValidPhone(phone)) {
        Toast.makeText(context, "Please enter valid phone no", Toast.LENGTH_SHORT).show()
        return
    }
    if (password.isEmpty()) {
        Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show()
        return
    }
    Toast.makeText(context, "User is registered successful...", Toast.LENGTH_SHORT).show()
    viewModel.registerUser(name, email, phone, password)
    onNavigate(
        LoginRoutes.LoginScreen.toRoute(),
        "",
        LoginRoutes.RegistrationScreen.toRoute(),
        true
    )
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreenPreview() {
    ComposeAppTheme(false) {
        RegistrationScreen(
            viewModel = LoginRegistrationViewmodel(),
            onNavigate = { route, _, _, _ -> },
            onBack = {}
        )
    }
}