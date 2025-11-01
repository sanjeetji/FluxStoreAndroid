package com.flux.store.ui.screens.productDetail

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.flux.store.R
import com.flux.store.fakeData.fakeNetwork.FakePreview
import com.flux.store.fakeData.fakeNetwork.exploreItemFakeData
import com.flux.store.fakeData.fakeNetwork.homeCategoryFakeData
import com.flux.store.fakeData.fakeNetwork.homePageFakeData
import com.flux.store.helper.Helper.twoUpCardWidth
import com.flux.store.helper.HomeScreenEnum
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.repository.ProductRepository
import com.flux.store.ui.screens.dynamicHelperView.BannerOutsideTitle
import com.flux.store.viewmodel.ProductListViewModel
import kotlin.collections.chunked

@Composable
fun ProductExploreScreen(
    viewModel: ProductListViewModel,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController,
) {
    val context = LocalContext.current
    val recentSearchData = viewModel.recentSearchData.collectAsState().value
    val inPreview = LocalInspectionMode.current
    var searchItem by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val tag = "SearchingExploreScreen"
    val focusManager = LocalFocusManager.current
    val homeBannerData = viewModel.homeBannerData.collectAsState().value
//    viewModel.setShowBottomBar(true)

    val searchAction = {
        focusManager.clearFocus()
        Toast.makeText(
            context,
            "Searching for: $searchItem",
            Toast.LENGTH_SHORT
        ).show()
    }

    val performSearch = { query: String ->
        searchItem = query
        searchAction()
    }

    BackHandler {
        navController.popBackStack()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
            .background(color = MaterialTheme.colorScheme.primary)
    )
    {
        Spacer(modifier = Modifier.height(22.dp))

        Image(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "back button",
            modifier = Modifier
                .padding(start = 16.dp)
                .shadow(1.dp, shape = RoundedCornerShape(50.dp), clip = true)
                .clickable {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.height(22.dp))
        // Search Bar
        val backgroundColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.10f)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
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
                            searchAction()
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            Text(
                text = tr(R.string.recent_search),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete All Button",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.clickable {
//                    viewModel.clearRecentSearches()
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recentSearchData) { data ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.recentSearchName,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                performSearch(data.recentSearchName)
                            }
                    )
                    Image(
                        painter = painterResource(R.drawable.img_cross),
                        contentDescription = "Delete Button",
                        modifier = Modifier.size(28.dp)
                            .clickable {
//                            viewModel.removeRecentSearch(data)
                            },
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn {
            for (pageData in homeBannerData) {
                Log.d(tag, "Page Data: $pageData")
                when (pageData.viewType) {

                    HomeScreenEnum.HORIZONTAL_WIDE_BANNER_LIST_VIEW.value -> {
                        Log.d(tag, "LARGE_WIDE_BANNER_LIST_VIEW : ${pageData.data}")
                        val itemWidth = screenWidth * 0.86f  // tweak 0.80–0.90 to taste

                        item {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            )
                            {
                                Text(
                                    text = pageData.dataHeader.headerTitle,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 16.dp)
                                )
                                Text(
                                    text = pageData.dataHeader.viewAll,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(end = 8.dp)
                                )
                            }
                            LazyRow {
                                items(pageData.data) { index ->
                                    BannerOutsideTitle(
                                        index,
                                        itemWidth = itemWidth,
                                        imageHeight = 170.dp
                                    )
                                }
                            }
                        }
                    }

                    HomeScreenEnum.VERTICAL_WIDE_BANNER_LIST_VIEW.value -> {
                        item {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            )
                            {
                                Text(
                                    text = pageData.dataHeader.headerTitle,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 16.dp)
                                )
                                Text(
                                    text = pageData.dataHeader.viewAll,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(end = 8.dp)
                                )
                            }
                        }
                        val itemWidth = screenWidth * 1f  // tweak 0.80–0.90 to taste
                        items(pageData.data) { index ->
                            BannerOutsideTitle(
                                banner = index,
                                itemWidth = itemWidth,
                                imageHeight = 170.dp
                            )
                        }
                    }

                    HomeScreenEnum.HORIZONTAL_TALL_BANNER_LIST_VIEW.value -> {
                        // Show ~2 cards per screen width while horizontally scrolling
                        val itemWidth = twoUpCardWidth(screenWidth)
                        item {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            )
                            {
                                Text(
                                    text = pageData.dataHeader.headerTitle,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 16.dp)
                                )
                                Text(
                                    text = pageData.dataHeader.viewAll,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(end = 8.dp)
                                )
                            }
                            Spacer(Modifier.height(2.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                contentPadding = PaddingValues(
                                    6.dp
                                )
                            ) {
                                items(pageData.data) { banner ->
                                    BannerOutsideTitle(
                                        banner = banner,
                                        itemWidth = itemWidth,
                                        imageHeight = 170.dp,
                                        isTallBanner = true
                                    )
                                }
                            }
                        }
                    }

                    HomeScreenEnum.VERTICAL_TALL_BANNER_LIST_VIEW.value -> {
                        item {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            )
                            {
                                Text(
                                    text = pageData.dataHeader.headerTitle,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 16.dp)
                                )
                                Text(
                                    text = pageData.dataHeader.viewAll,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(end = 8.dp)
                                )
                            }
                        }
                        // Render two columns per row using chunked(2)
                        val itemWidth = screenWidth / 2f
                        val rows = pageData.data.chunked(2)
                        items(rows) { rowBanners ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(1.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                // Left card
                                BannerOutsideTitle(
                                    banner = rowBanners[0],
                                    itemWidth = itemWidth,
                                    isTallBanner = true
                                )
                                // Right card (if present)
                                if (rowBanners.size > 1) {
                                    BannerOutsideTitle(
                                        banner = rowBanners[1],
                                        itemWidth = itemWidth,
                                        isTallBanner = true
                                    )
                                } else {
                                    // keep row balanced when odd count
                                    Spacer(modifier = Modifier.width(itemWidth))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Home – Light")
@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Home – Dark")
@Composable
private fun ProductExploreScreenPreview() {
    FakePreview(
        fakeData = Unit,
        useUiState = false,
        onSuccess = {
            // ---- Fake ViewModel with instant data ----
            val fakeVM = remember {
                object : ProductListViewModel(ProductRepository()) {
                    init {
                        previewInjectData(
                            categories = homeCategoryFakeData,
                            pageData = homePageFakeData,
                            exploreData = exploreItemFakeData,
                        )
                    }
                }
            }

            CompositionLocalProvider(
                LocalBottomBarVisible provides remember { mutableStateOf(true) },
                LocalIsDarkTheme provides remember { mutableStateOf(false) }
            ) {
                ProductExploreScreen(
                    viewModel = fakeVM,
                    onNavigate = { route, _, _, _ -> println("Navigating to $route") },
                    navController = rememberNavController(),
                )
            }
        }
    )
}