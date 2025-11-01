package com.flux.store.fakeData.fakeNetwork

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalInspectionMode
import com.flux.store.viewmodel.UiState

/**
 * Universal preview helper.
 * • Works with UiState + ViewModel **or** direct data.
 * • No extra @Preview wrapper needed – it applies the theme itself.
 */
@Composable
inline fun <reified T> FakePreview(
    fakeData: T,
    darkTheme: Boolean = false,
    showError: Boolean = false,
    useUiState: Boolean = true,
    crossinline onError: @Composable (String) -> Unit = {},
    crossinline onLoading: @Composable () -> Unit = {},
    crossinline onSuccess: @Composable (T) -> Unit
) {
    val isInPreview = LocalInspectionMode.current

    // -------------------------------------------------
    // ALWAYS wrap with the app theme (even in preview)
    // -------------------------------------------------
    PreviewWrapper(darkTheme = darkTheme) {

        // ---------- PREVIEW PATH (instant) ----------
        if (isInPreview) {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                onSuccess(fakeData)
            }
            return@PreviewWrapper
        }

        // ---------- RUNTIME PATH ----------
        if (useUiState) {
            val fakeVM = remember { FakeViewModel<T>(FakeRepository()) }
            val uiState by fakeVM.uiState.collectAsState()

            LaunchedEffect(Unit) {
                if (showError) fakeVM.previewError("Preview error")
                else fakeVM.previewSuccess(fakeData)
            }

            when (val state = uiState) {
                UiState.Loading -> onLoading()
                is UiState.Error -> onError(state.message)
                is UiState.Success -> onSuccess(state.data.data as T)
            }
        } else {
            // Direct data (no ViewModel)
            if (showError) onError("Preview error")
            else onSuccess(fakeData)
        }
    }
}