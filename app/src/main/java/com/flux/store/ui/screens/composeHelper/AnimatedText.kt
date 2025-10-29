package com.flux.store.ui.screens.composeHelper

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * 🎨 Modern Animated Text Styles
 * Safe, reusable, and lifecycle-aware.
 *
 * Use with: AnimatedText(type = AnimationType.Shimmer, text = "Fetching Data")
 */

@Composable
fun AnimatedText(
    text: String,
    type: AnimationType = AnimationType.Pulse,
    modifier: Modifier = Modifier
) {
    when (type) {
        AnimationType.Pulse -> PulseText(text, modifier)
        AnimationType.Fade -> FadeText(text, modifier)
        AnimationType.Wave -> WaveText(text, modifier)
        AnimationType.Typewriter -> TypewriterText(text, modifier)
        AnimationType.Float -> FloatText(text, modifier)
        AnimationType.Heartbeat -> HeartbeatText(text, modifier)
        AnimationType.Shimmer -> ShimmerText(text, modifier)
        AnimationType.GradientSlide -> GradientSlideText(text, modifier)
        AnimationType.ColorShift -> ColorShiftText(text, modifier)
        AnimationType.DotBounce -> DotBounceText(text, modifier)
    }
}

/* --------------------------- TEXT STYLES ---------------------------- */

@Composable
private fun FadeText(text: String, modifier: Modifier) {
    val alpha by rememberInfiniteTransition(label = "fade").animateFloat(
        initialValue = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse),
        label = "fadeAnim"
    )

    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha),
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
    )
}

@Composable
private fun WaveText(text: String, modifier: Modifier) {
    Row(modifier) {
        text.forEachIndexed { index, char ->
            val offsetY by rememberInfiniteTransition(label = "wave").animateFloat(
                initialValue = 0f, targetValue = -6f,
                animationSpec = infiniteRepeatable(
                    tween(600, delayMillis = index * 100, easing = EaseInOutCubic),
                    RepeatMode.Reverse
                ),
                label = "waveAnim$index"
            )
            Text(
                text = char.toString(),
                modifier = Modifier.offset(y = offsetY.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
            )
        }
    }
}

@Composable
private fun PulseText(text: String, modifier: Modifier) {
    val scale by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0.95f, targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            tween(700, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "pulseAnim"
    )
    Text(
        text = text,
        modifier = modifier.scale(scale),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
    )
}

@Composable
private fun TypewriterText(text: String, modifier: Modifier) {
    var visibleChars by remember { mutableStateOf(0) }
    LaunchedEffect(text) {
        while (true) {
            if (visibleChars < text.length) visibleChars++ else visibleChars = 0
            delay(100)
        }
    }
    Text(
        text = buildString {
            append(text.take(visibleChars))
            append("|")
        },
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
        modifier = modifier
    )
}

@Composable
private fun FloatText(text: String, modifier: Modifier) {
    val float by rememberInfiniteTransition(label = "float").animateFloat(
        initialValue = -4f, targetValue = 4f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = EaseInOutSine),
            RepeatMode.Reverse
        ),
        label = "floatAnim"
    )
    Text(
        text = text,
        modifier = modifier.offset(y = float.dp),
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
    )
}

@Composable
private fun HeartbeatText(text: String, modifier: Modifier) {
    val beat by rememberInfiniteTransition(label = "beat").animateFloat(
        initialValue = 0.9f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(600, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "beatAnim"
    )
    Text(
        text = text,
        modifier = modifier.scale(beat),
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
    )
}

@Composable
private fun ShimmerText(text: String, modifier: Modifier) {
    val shimmer = rememberInfiniteTransition(label = "shimmer")
    val offset by shimmer.animateFloat(
        initialValue = 0f, targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)),
        label = "shimmerAnim"
    )
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 15.sp,
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                ),
                start = Offset(offset, 0f),
                end = Offset(offset + 200f, 0f)
            )
        )
    )
}

@Composable
private fun GradientSlideText(text: String, modifier: Modifier) {
    val transition = rememberInfiniteTransition(label = "gradient")
    val offset by transition.animateFloat(
        initialValue = 0f, targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "offsetAnim"
    )
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 15.sp,
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                ),
                start = Offset(offset, 0f),
                end = Offset(offset + 200f, 0f)
            )
        )
    )
}

/* 🆕 EXTRAS */

@Composable
private fun ColorShiftText(text: String, modifier: Modifier) {
    val colorAnim = rememberInfiniteTransition(label = "colorShift").animateColor(
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.tertiary,
        animationSpec = infiniteRepeatable(
            tween(1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "colorAnim"
    )
    Text(
        text = text,
        modifier = modifier,
        color = colorAnim.value,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
    )
}

@Composable
private fun DotBounceText(text: String, modifier: Modifier) {
    var dotCount by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(400)
            dotCount = (dotCount + 1) % 4
        }
    }
    val base = "$text" + ".".repeat(dotCount)
    val scale by rememberInfiniteTransition(label = "dotPulse").animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(tween(600, easing = EaseInOutCubic), RepeatMode.Reverse),
        label = "scaleDot"
    )
    Text(
        text = base,
        modifier = modifier.scale(scale),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
    )
}

/* --------------------------- ENUM ---------------------------- */

enum class AnimationType {
    Pulse, Fade, Wave, Typewriter, Float, Heartbeat, Shimmer, GradientSlide, ColorShift, DotBounce
}
