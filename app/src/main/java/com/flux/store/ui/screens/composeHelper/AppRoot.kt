package com.flux.store.ui.screens.composeHelper

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.flux.store.helper.AppEvent
import com.flux.store.helper.AppEventBus
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppRoot(
    eventBus: AppEventBus,
    onForceLogout: () -> Unit
) {
    val events = eventBus.events
    var tokenExpiredMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        events.collectLatest { event ->
            if (event is AppEvent.TokenExpired) {
                tokenExpiredMessage = event.reason ?: "Session expired"
            }
        }
    }

    if (tokenExpiredMessage != null) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Session Expired") },
            text = { Text(tokenExpiredMessage!!) },
            confirmButton = {
                TextButton(onClick = {
                    tokenExpiredMessage = null
                    onForceLogout()
                }) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppRootPreview() {
    val fakeBus = remember { AppEventBus() } // ✅ Works fine

    AppRoot(
        eventBus = fakeBus,
        onForceLogout = {}
    )
}
