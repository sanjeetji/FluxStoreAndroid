package com.flux.store.ui.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.flux.store.R
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.repository.HomeRepository
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.viewmodel.HomeViewModel

@Composable
fun ExploreScreen(
    viewModel: HomeViewModel,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    onMenuClick: () -> Unit,
    onBack: () -> Unit = { navController.popBackStack() }
) {
    val context = LocalContext.current
    viewModel.setShowBottomBar(true)
    BackHandler {
        onBack()
    }
    CompositionLocalProvider(
        LocalBottomBarVisible provides remember { mutableStateOf(viewModel.showBottomBar.value) }) {
        MainScreenScaffold(
            viewModel = viewModel,
            navController = navController,
            titleResId = R.string.explore,
            onMenuClick = onMenuClick,
            onNotificationClick = {
                Toast.makeText(context, "Notifications clicked", Toast.LENGTH_SHORT).show()
            },
            onNavigate = onNavigate,
            drawerState = drawerState
        ) { modifier ->
            ExploreScreenContent(
                viewModel = viewModel,
                onNavigate = onNavigate,
                navController = navController,
                modifier = modifier,
                drawerState = drawerState
            )
        }
    }
}

@Composable
fun ExploreScreenContent(
    viewModel: HomeViewModel,
    onNavigate: (String, Any?, String?, Boolean) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val explorePageData = viewModel.explorePageData.collectAsState().value
    val inPreview = LocalInspectionMode.current
    var searchItem by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var expandedIndex by remember { mutableIntStateOf(-1) } // -1 means none expanded
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val tag = "ExploreScreen"
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        // Search Bar
        val backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.10f)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Search Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(backgroundColor) // main background
                    .border(0.dp, Color.Transparent, RoundedCornerShape(20.dp))
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                TextField(
                    value = searchItem,
                    onValueChange = { newValue -> searchItem = newValue },
                    placeholder = {
                        Text(
                            text = tr(R.string.hint_search),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                            Toast.makeText(
                                context,
                                "Searching for: $searchItem",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Filter Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(backgroundColor)
                    .height(50.dp)
                    .width(60.dp)
                    .clickable {
                        Toast.makeText(context, "Filter clicked", Toast.LENGTH_SHORT).show()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "Filter Icon",
                    modifier = Modifier.size(28.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        }


        Spacer(modifier = Modifier.size(16.dp))

        // Banners

        // Banners - Scrollable LazyColumn with expandable items
        // Banners - Scrollable LazyColumn with expandable items
        LazyColumn {
            items(explorePageData) { pageData ->
                val currentIndex = explorePageData.indexOf(pageData)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Banner Image with clickable for expand/collapse
                    val imageModifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            // Toggle expansion: if already expanded, collapse; else expand and collapse others
                            expandedIndex = if (expandedIndex == currentIndex) -1 else currentIndex
                        }

                    if (inPreview || pageData.drawableImage is Int) {
                        // PREVIEW or a local drawable resource → use Image + painterResource
                        val resId = pageData.drawableImage as? Int ?: R.drawable.img_transparent
                        Image(
                            painter = painterResource(resId),
                            contentDescription = "Banner Name",
                            contentScale = ContentScale.FillBounds,
                            modifier = imageModifier
                        )
                    } else {
                        // RUNTIME & it's a URL → use Coil's AsyncImage directly
                        AsyncImage(
                            model = pageData.bannerImage /* String URL */,
                            contentDescription = "Banner Name",
                            placeholder = painterResource(R.drawable.img_transparent),
                            error = painterResource(R.drawable.img_transparent),
                            contentScale = ContentScale.Crop,
                            modifier = imageModifier
                        )
                    }

                    // Expandable Inner Items (Categories)
                    if (expandedIndex == currentIndex) {
                        pageData.bannerCategory.forEach { categoryData ->
                            Column {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .clickable {
                                            // Navigate to category detail screen
                                            Toast.makeText(
                                                context,
                                                categoryData.categoryName,
                                                Toast.LENGTH_SHORT
                                            ).show()
//                                            onNavigate(
//                                                targetRoute, null, SplashRoutes.SplashScreen.toRoute(), true
//                                            )
                                        }
                                ) {
                                    Text(
                                        text = categoryData.categoryName,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .wrapContentWidth()
                                    ) {
                                        Text(
                                            text = categoryData.totalItem.toString(),
                                            color = MaterialTheme.colorScheme.tertiary,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Icon(
                                            modifier = Modifier.size(16.dp),
                                            imageVector = Icons.Rounded.ArrowForwardIos,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.tertiary
                                        )
                                    }
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(color = MaterialTheme.colorScheme.outline)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ExploreScreenPreview() {
    ComposeAppTheme(false) {
        CompositionLocalProvider(
            LocalBottomBarVisible provides remember { mutableStateOf(true) },
            LocalIsDarkTheme provides remember { mutableStateOf(false) }) {
            val previewVM = remember { HomeViewModel(HomeRepository()) }
            ExploreScreen(
                viewModel = previewVM,
                onNavigate = { route, _, _, _ -> println("Navigating to $route") },
                navController = rememberNavController(),
                drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
                onMenuClick = {})
        }
    }
}