package com.flux.store.helper

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.flux.store.navigation.routes.Routes

/**
 * Navigate to any screen in your `Routes`, carrying an optional payload.
 * Does *not* clear the backstack.
 */
fun NavHostController.navigateTo(
    target: Routes,
    payload: Any? = null,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    // 1) stash the payload
    currentBackStackEntry
        ?.savedStateHandle
        ?.set("navPayload", payload)

    // 2) navigate
    this.navigate(target.toRoute()) {
        launchSingleTop = true
        builder?.invoke(this)
    }
}

/**
 * Clear *all* previous destinations (popping up to the graph’s start destination inclusive),
 * then navigate to [target].  Optionally pass a payload.
 */
// in com.flux.store.helper (replace your old clearAndNavigateTo)

fun NavHostController.clearAndNavigateTo(
    target: Routes,
    payload: Any? = null
) {
    // stash payload if you need it downstream
    currentBackStackEntry
        ?.savedStateHandle
        ?.set("navPayload", payload)

    navigate(target.toRoute()) {
        launchSingleTop = true
        // Pop *every* entry in the current NavGraph
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

