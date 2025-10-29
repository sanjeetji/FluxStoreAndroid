package com.flux.store.ui.screens.dynamicHelperView

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flux.store.R
import com.flux.store.ui.theme.ComposeAppTheme
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalViewPagerView(
    viewPagerData: List<Int>,
    onBackClick: () -> Unit,
    onFavClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier // Added modifier parameter
) {
    val inPreview = LocalInspectionMode.current
    var isUserInteracting by remember { mutableStateOf(false) }
    val viewPagerState = rememberPagerState(pageCount = { viewPagerData.size })
    var isFavourite by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Detect manual scrolling
    LaunchedEffect(viewPagerState.isScrollInProgress) {
        if (viewPagerState.isScrollInProgress) {
            isUserInteracting = true
            Log.d("IntroScreen", "Manual scroll detected: isUserInteracting = true")
        } else if (isUserInteracting && !viewPagerState.isScrollInProgress) {
            delay(500)
            isUserInteracting = false
            Log.d("IntroScreen", "Manual scroll ended: isUserInteracting = false")
        }
    }

    // Auto-scroll logic
    LaunchedEffect(Unit) {
        while (true) {
            if (!isUserInteracting) {
                delay(1000)
                val nextPage = (viewPagerState.currentPage + 1) % viewPagerData.size
                viewPagerState.animateScrollToPage(nextPage)
            }
            delay(100)
        }
    }

    HorizontalPager(
        state = viewPagerState,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(0.dp)
            .semantics { contentDescription = "Image carousel" }
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(color = MaterialTheme.colorScheme.outline)
        ) {
            // Display Image from URL or Local Drawable
            if (inPreview || viewPagerData[page] is Int) {
                val resId = viewPagerData[page] as? Int ?: R.drawable.img_girl_1
                Image(
                    painter = painterResource(resId),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(440.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                AsyncImage(
                    model = viewPagerData[page],
                    contentDescription = "",
                    placeholder = painterResource(R.drawable.img_girl_1),
                    error = painterResource(R.drawable.img_home_banner),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(440.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            // Back Button (on the left side)
            Image(
                painter = painterResource(R.drawable.ic_back_1),
                contentDescription = "Back button",
                modifier = Modifier
                    .size(42.dp)
                    .padding(start = 12.dp, top = 12.dp)
                    .clickable {
                        onBackClick()
                        Log.d("ProductDetails", "Back button clicked")
                    }
            )

            // Favorite Icon (on the right side)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(42.dp)
                    .padding(end = 12.dp, top = 12.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .clickable {
                        isFavourite = !isFavourite
                        onFavClick(isFavourite)
                        val msg = if (isFavourite) "Added to favourites" else "Removed from favourites"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavourite) "Unfavourite" else "Favourite",
                    tint = if (isFavourite) Color.Red else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
            }

            // Page Indicators
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .semantics { contentDescription = "Page indicators" }
            ) {
                items(viewPagerData.size) { index ->
                    val isSelected = index == viewPagerState.currentPage
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(12.dp)
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = CircleShape
                            )
                            .semantics { contentDescription = "Page indicator ${index + 1}" }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun VerticalViewPagerViewPreview() {
    ComposeAppTheme(false) {
        VerticalViewPagerView(
            viewPagerData = listOf(
                R.drawable.img_prod_1,
                R.drawable.img_prod_2,
                R.drawable.img_prod_3,
            ),
            onBackClick = { Log.d("ProductDetails", "Back button clicked") },
            onFavClick = { isFav -> Log.d("ProductDetails", "Favorite status: $isFav") }
        )
    }
}