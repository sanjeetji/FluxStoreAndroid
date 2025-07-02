package com.flux.store.helper// in some file like NavigationKeys.kt
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.MutableState

val LocalBottomBarVisible =
  compositionLocalOf<MutableState<Boolean>> { 
    error("No BottomBar visibility provided") 
  }
