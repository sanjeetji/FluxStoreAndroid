package com.flux.store.helper

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


object ComposeFileHelper {

    @Composable
    fun defaultTextFieldColors(): TextFieldColors {
        val cs = MaterialTheme.colorScheme
        return TextFieldDefaults.colors(
            focusedContainerColor       = cs.tertiary.copy(alpha = 0.6f),
            unfocusedContainerColor     = cs.primary,
            focusedTextColor            = cs.onTertiary,
            unfocusedTextColor          = cs.onBackground,
            focusedPlaceholderColor     = cs.primary,
            unfocusedPlaceholderColor   = cs.onPrimary.copy(alpha = 0.5f),
            focusedIndicatorColor       = cs.tertiary.copy(alpha = 0.3f),
            unfocusedIndicatorColor     = cs.primary,
            unfocusedLeadingIconColor   = cs.onPrimary,
            focusedLeadingIconColor     = cs.primary
        )
    }

    @Composable
    fun Modifier.edgeToEdgePadding(): Modifier =
        this.then(Modifier.windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)))

}