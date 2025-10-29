package com.flux.store.ui.screens.composeHelper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.ui.theme.ComposeAppTheme
import kotlinx.coroutines.delay

@Composable
fun LoadingView(message: String? = null) {
    val rotation = rememberInfiniteTransition(label = "rotate").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnim"
    )

    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
    )

    // 🔤 Animate dots at end of text
    var dotCount by remember { mutableStateOf(0) }
    LaunchedEffect(message) {
        while (true) {
            delay(500)
            dotCount = (dotCount + 1) % 4 // cycle through 0–3 dots
        }
    }

    // 💫 Subtle text pulse effect
    val pulseAnim = rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "pulseAnim"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 🔵 Animated rotating gradient ring
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .rotate(rotation.value)
                    .background(
                        brush = Brush.sweepGradient(gradientColors),
                        shape = CircleShape
                    )
                    .padding(6.dp)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
            )

            // ✨ Animated message with dots + pulse
            AnimatedVisibility(visible = !message.isNullOrEmpty()) {
                Spacer(Modifier.height(16.dp))
                AnimatedText(
                    text = "Fetching Data",
                    type = AnimationType.Typewriter
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingViewPreview() {
    ComposeAppTheme(darkTheme = false) {
        CompositionLocalProvider(
            LocalBottomBarVisible provides remember { mutableStateOf(true) },
            LocalIsDarkTheme provides remember { mutableStateOf(false) }
        ) {
            LoadingView(message = "Fetching data")
        }
    }
}
