package com.flux.store.ui.screens.dynamicHelperView

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


@Composable
fun StarRatingBar(
    rating: Double,
    maxStars: Int = 5,
    starSize: Dp = 18.dp,
    spacing: Dp = 4.dp
) {
    Log.d("StarRatingBar", "Rendering with rating: $rating")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        val safeRating = rating.coerceIn(0.0, maxStars.toDouble())
        repeat(maxStars) { index ->
            val filledPortion = (safeRating - index).coerceIn(0.0, 1.0)
            Log.d("StarRatingBar", "Star $index: filledPortion=$filledPortion")
            Box(modifier = Modifier.size(starSize)) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color(0xFFBDBDBD),
                    modifier = Modifier.fillMaxSize()
                )
                if (filledPortion > 0) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                val clipWidth = size.width * filledPortion.toFloat()
                                clipRect(0f, 0f, clipWidth, size.height) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            }
        }
    }
}
