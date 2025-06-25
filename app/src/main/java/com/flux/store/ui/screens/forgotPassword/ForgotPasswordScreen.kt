package com.flux.store.ui.screens.forgotPassword

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.R
import com.flux.store.viewmodel.ForgotPasswordViewmodel

@Composable
fun ForgotPassword(
    viewModel: ForgotPasswordViewmodel,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(start = 22.dp, end = 32.dp)) {
        Spacer(modifier = Modifier.height(18.dp))
        Image(painterResource(R.drawable.ic_back), contentDescription = "back button")
        Spacer(modifier = Modifier.height(24.dp))
        Text(stringResource(R.string.forgot_password), style = TextStyle(fontSize = 24.sp))
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            stringResource(R.string.forgot_password_description),
            style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Default)
        )
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    ForgotPassword(
        viewModel = ForgotPasswordViewmodel(),
        onNavigate = { route, _, _, _ -> },
        onBack = {}
    )
}