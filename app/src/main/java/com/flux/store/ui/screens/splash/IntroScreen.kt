package com.flux.store.ui.screens.splash

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.flux.store.R
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.model.response.IntroResponse
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.ui.theme.WhiteColor
import com.flux.store.viewmodel.IntroViewmodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun IntroScreen(
    viewModel: IntroViewmodel,
    onBack: () -> Unit,
    onNavigate: (
        route: String,
        payload: Any?,
        popUpToRoute: String?,
        inclusive: Boolean
    ) -> Unit
) {

    val introData = listOf(
        IntroResponse(
            tr(R.string.first_intro_title),
            tr(R.string.first_intro_description),
            R.drawable.img_first_intro
        ),
        IntroResponse(
            tr(R.string.second_intro_title),
            tr(R.string.second_intro_description),
            R.drawable.img_second_intro
        ),
        IntroResponse(
            tr(R.string.third_intro_title),
            tr(R.string.third_intro_description),
            R.drawable.img_third_intro
        ),
    )

    val pagerState = rememberPagerState(pageCount = { introData.size })
    val coroutineScope = rememberCoroutineScope()
    var isUserInteracting by remember { mutableStateOf(false) }

    // Detect manual scrolling
    LaunchedEffect(pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) {
            isUserInteracting = true
            Log.d("IntroScreen", "Manual scroll detected: isUserInteracting = true")
        } else if (isUserInteracting && !pagerState.isScrollInProgress) {
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
                val nextPage = (pagerState.currentPage + 1) % introData.size
                pagerState.animateScrollToPage(nextPage)
            }
            delay(100)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
        {
            // Top Text Area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
            }
            // Bottom Half with Overlay
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(.8f)
                    .fillMaxWidth()
                    .background(Color.Transparent)
            )
            {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.9f)
                        .semantics { contentDescription = "Image carousel" }
                ) { page ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 50.dp)
                                .semantics { contentDescription = introData[page].title },
                            text = introData[page].title,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Black,
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp)
                                .semantics { contentDescription = introData[page].description },
                            text = introData[page].description,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black,
                        )
                        AsyncImage(
                            model = introData[page].image,
                            contentDescription = "Intro Image ${page + 1}",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(introData.size) { index ->
                        val isSelected = index == pagerState.currentPage
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(20.dp)
                                .background(
                                    color = if (isSelected) Color.White else Color.Transparent,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = CircleShape
                                )
                                .semantics {
                                    contentDescription = "Page indicator ${index + 1}"
                                }
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth()
                    .background(Color.Transparent)
            )
            {

                Button(
                    onClick = {
                        Log.e(
                            "OnCLicked....",
                            "Current Page is ${pagerState.currentPage} Index is ${introData.size} Last index is ${introData.lastIndex}"
                        )
                        if (pagerState.currentPage == introData.lastIndex) {
                            handleShoppingNow(onNavigate)
                        } else {
                            coroutineScope.launch {
                                isUserInteracting = true
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                isUserInteracting = false
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                        .width(200.dp)
                        .height(50.dp)
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.outline,
                            CircleShape
                        ) // #6B6B6B (dark) or #8A8A8A (light)
                        .semantics { contentDescription = "Navigate to next page or home" },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = if (pagerState.currentPage == introData.lastIndex)
                            tr(R.string.shopping_now)
                        else
                            tr(R.string.next),
                        color = WhiteColor,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

fun handleShoppingNow(onNavigate: (target: String, payload: Any?, String?, Boolean) -> Unit) {
    onNavigate(LoginRoutes.LoginScreen.toRoute(), null, null, false)
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun IntroScreenView() {
    ComposeAppTheme {
        IntroScreen(
            IntroViewmodel(), {},
            onNavigate = { route, _, _, _ -> },
        )
    }
}

