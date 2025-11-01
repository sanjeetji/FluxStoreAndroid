package com.flux.store.helper

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

enum class NavigationMode {
    FOUR_BUTTON, THREE_BUTTON, TWO_BUTTON, GESTURE, UNKNOWN
}

fun Context.readNavigationMode(): NavigationMode {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            when (Settings.Secure.getInt(contentResolver, "navigation_mode", 0)) {
                0 -> NavigationMode.FOUR_BUTTON
                1 -> NavigationMode.THREE_BUTTON
                2 -> NavigationMode.TWO_BUTTON
                3 -> NavigationMode.GESTURE
                else -> NavigationMode.UNKNOWN
            }
        } else NavigationMode.THREE_BUTTON
    } catch (_: Exception) {
        NavigationMode.UNKNOWN
    }
}

fun Context.observeNavigationMode() = callbackFlow {
    val uri: Uri = Settings.Secure.getUriFor("navigation_mode")

    val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            trySend(readNavigationMode())
        }
    }

    contentResolver.registerContentObserver(uri, false, observer)
    trySend(readNavigationMode()) // initial value

    awaitClose { contentResolver.unregisterContentObserver(observer) }
}.distinctUntilChanged()
