package com.flux.store.fakeData.fakeNetwork

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.flux.store.ui.theme.ComposeAppTheme

/**
 * Simple wrapper that guarantees the preview is rendered inside the app theme.
 * Use it in every @Preview (or let FakePreview call it internally – see the final version).
 */
@Composable
fun PreviewWrapper(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    ComposeAppTheme(darkTheme = darkTheme) {
        Surface {
            content()
        }
    }
}