package com.flux.store.helper.localizationHelper


import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

val LocalLocalizationManager = staticCompositionLocalOf<LocalizationManager> {
    error("No LocalizationManager provided")
}
val LocalStrings = staticCompositionLocalOf<Map<String,String>> { emptyMap() }
@Composable fun String.t() = LocalStrings.current[this] ?: this

@Composable
fun tr(@StringRes id: Int, key: String = LocalContext.current.resources.getResourceEntryName(id)): String {
    // 1️⃣ try remote
    val remote = LocalStrings.current[key]
    if (!remote.isNullOrBlank()) return remote
    // 2️⃣ else fall back to xml
    return stringResource(id)
}
