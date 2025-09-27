package com.flux.store.ui.screens.dynamicHelperView

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flux.store.model.response.HomeBanner
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.flux.store.R
import com.flux.store.model.response.DataHeader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerView(viewPagerData: List<HomeBanner>) {
    // Create a PagerState only for this banner list:
    val inPreview = LocalInspectionMode.current
    var isUserInteracting by remember { mutableStateOf(false) }
    val viewPagerState = rememberPagerState(pageCount = {viewPagerData.size})

    // Detect manual scrolling
    LaunchedEffect(viewPagerState.isScrollInProgress) {
        if (viewPagerState.isScrollInProgress) {
            isUserInteracting = true
            Log.d("IntroScreen", "Manual scroll detected: isUserInteracting = true")
        } else if (isUserInteracting && !viewPagerState.isScrollInProgress) {
            delay(500) // Wait for user to settle
            isUserInteracting = false
            Log.d("IntroScreen", "Manual scroll ended: isUserInteracting = false")
        }
    }

    // Auto-scroll logic (simplified for brevity)
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
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(22.dp)
            .semantics { contentDescription = "Image carousel" }
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)            // match your image height
        ){
            /*AsyncImage(
                model = viewPagerData[page].bannerImage,
                contentDescription = "Intro Image ${page + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            )*/


            if (inPreview || viewPagerData[page].bannerImage is Int) {
                // PREVIEW or a local drawable resource → use Image + painterResource
                val resId = viewPagerData[page].bannerImage as? Int ?: R.drawable.img_home_banner
                Image(
                    painter = painterResource(resId),
                    contentDescription = viewPagerData[page].bannerTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            else {
                // RUNTIME & it's a URL → use Coil's AsyncImage directly
                AsyncImage(
                    model = viewPagerData[page].bannerImage /* String URL */,
                    contentDescription = viewPagerData[page].bannerTitle,
                    placeholder = painterResource(R.drawable.img_home_banner),
                    error       = painterResource(R.drawable.img_home_banner),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Text(text = viewPagerData[page].bannerTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                minLines = 2,
                overflow = TextOverflow.Clip,
                softWrap = false,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 40.dp, end = 16.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)    // tweak as needed
                    .semantics { contentDescription = "Page indicators" }
            ) {
                items(viewPagerData.size) { index ->
                    val isSelected = index == viewPagerState.currentPage
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(12.dp)          // smaller dot size
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .semantics {
                                contentDescription = "Page indicator ${index + 1}"
                            }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun ViewPagerViewPreview() {
    ViewPagerView(
        listOf(
            HomeBanner(
                bannerId = 1,
                bannerTitle = "Banner Title",
                bannerDescription = "This text is for banner description",
                bannerImage = "https://images.unsplash.com/photo-1583316174775-bd6dc0e9f298?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                DataHeader("New Collection", "View All")
            )
        )
    )
}