package com.flux.store.helper

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class SlideDirection {
    Top, Bottom, Left, Right
}

@Composable
fun AnimatedEntrance(
    direction: SlideDirection = SlideDirection.Bottom,
    delayMillis: Int = 0,
    durationMillis: Int = 700,
    distance: Dp = 100.dp,
    content: @Composable (modifier: Modifier) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        isVisible = true
    }

    val transition = updateTransition(targetState = isVisible, label = "AnimatedEntrance")

    val offset by transition.animateDp(
        transitionSpec = { tween(durationMillis) },
        label = "SlideOffset"
    ) { visible ->
        if (visible) 0.dp else distance
    }

    val animatedAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis) },
        label = "Alpha"
    ) { visible -> if (visible) 1f else 0f }

    val modifier = Modifier.graphicsLayer {
        alpha = animatedAlpha
        when (direction) {
            SlideDirection.Top -> translationY = -offset.toPx()
            SlideDirection.Bottom -> translationY = offset.toPx()
            SlideDirection.Left -> translationX = -offset.toPx()
            SlideDirection.Right -> translationX = offset.toPx()
        }
    }

    content(modifier)
}
