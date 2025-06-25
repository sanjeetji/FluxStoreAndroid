package com.flux.store.ui.screens.loginRegistration

import android.annotation.SuppressLint
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.R
import com.flux.store.ui.theme.Black
import com.flux.store.ui.theme.White
import com.flux.store.utils.AppStateManager
import com.flux.store.utils.LocalLocalizationManager
import com.flux.store.utils.LocalStrings
import com.flux.store.utils.LocalizationManager
import com.flux.store.utils.t
import com.flux.store.utils.tr
import com.flux.store.viewmodel.LoginRegistrationViewmodel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun LanguagePickerScreen(
    viewModel: LoginRegistrationViewmodel, onNavigate: (
        route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean
    ) -> Unit, onBack: () -> Unit
) {

    val locMgr = LocalLocalizationManager.current
    val langs = listOf("en" to "English", "hi" to "हिन्दी", "fr" to "Français")
    var expanded by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf(langs.first().second) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        val current = locMgr.state.selectedLanguage.first()
        label = langs.find { it.first == current }?.second ?: "English"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp, top = 42.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = tr(R.string.please_select_any_language),
                style = TextStyle(fontSize = 16.sp, color = Black, textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                label,
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { expanded = true }
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp))
            DropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                langs.forEach { (code, name) ->
                    DropdownMenuItem(text = { Text(name) }, onClick = {
                        label = name
                        expanded = false
                        coroutineScope.launch {
                            locMgr.setLanguage(code)
                        }
                    })
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LanguagePickerScreenPreview() {
    val context = LocalContext.current
    val dummyMgr = LocalizationManager(
        state = AppStateManager(context)
    )
    val sampleStrings = mapOf(
        "please_select_any_language" to "Please select any language",
        "en" to "English",
        "hi" to "हिन्दी",
        "fr" to "Français"
    )
    CompositionLocalProvider(
        LocalLocalizationManager provides dummyMgr, LocalStrings provides sampleStrings
    ) {
        LanguagePickerScreen(
            viewModel = LoginRegistrationViewmodel(),
            onNavigate = { _, _, _, _ -> }, onBack = {})
    }
}

