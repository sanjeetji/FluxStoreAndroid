package com.flux.store.ui.screens.home

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.flux.store.repository.HomeRepository
import com.flux.store.ui.screens.dynamicHelperView.BannerOutsideTitle
import com.flux.store.ui.screens.dynamicHelperView.SingleBannerViewInsideTitle
import com.flux.store.ui.screens.dynamicHelperView.ViewPagerView
import com.flux.store.viewmodel.HomeViewModel
import kotlin.collections.chunked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    onMenuClick: () -> Unit
) {
    val context = LocalContext.current
    viewModel.setShowBottomBar(true)
    BackHandler {
        navController.popBackStack()
    }
    CompositionLocalProvider(
        LocalBottomBarVisible provides remember { mutableStateOf(viewModel.showBottomBar.value) }
    ) {
        MainScreenScaffold(
            viewModel = viewModel,
            navController = navController,
            titleResId = R.string.app_name1,
            onMenuClick = onMenuClick,
            onNotificationClick = {
                Toast.makeText(context, "Notifications clicked", Toast.LENGTH_SHORT).show()
            },
            onNavigate = onNavigate,
            drawerState = drawerState
        ) { contentModifier ->
            HomeScreenContentView(
                viewModel = viewModel,
                onNavigate = onNavigate,
                navController = navController,
                modifier = contentModifier,
                drawerState = drawerState
            )
        }
    }
}


@Composable
fun HomeScreenContentView(viewModel: HomeViewModel,
                          onNavigate: (String, Any?, String?, Boolean) -> Unit,
                          navController: NavHostController,
                          modifier: Modifier = Modifier,
                          drawerState: DrawerState) {
    val context = LocalContext.current
    val categoryData = viewModel.categoryData.collectAsState().value
    val homeBannerData = viewModel.homeBannerData.collectAsState().value
    var selectedIndex by remember { mutableIntStateOf(0) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val tag = "HomeScreen"
    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.primary))
    {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(25.dp))
                .padding(start = 4.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            itemsIndexed(categoryData) { index, category ->
                val isSelected = index == selectedIndex
                Column(
                    modifier = Modifier
                        .width(70.dp)
                        .clip(shape = RoundedCornerShape(25.dp))
                        .clickable {
                            selectedIndex = index
                            Toast.makeText(
                                context,
                                "Selected Category Name is ${category.categoryName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(shape = RoundedCornerShape(25.dp))
                            .background(
                                if (isSelected)
                                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
                                else
                                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painterResource(categoryData[index].categoryImage),
                            contentDescription = category.categoryName,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(
                                if (isSelected)
                                    MaterialTheme.colorScheme.onSecondary
                                else
                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    Spacer(Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.padding(bottom = 12.dp),
                        text = categoryData[index].categoryName,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onSecondary
                        else
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                    )
                }
            }
        }
        LazyColumn {

            for (pageData in homeBannerData) {
                Log.d(tag, "Page Data: $pageData")
                when (pageData.viewType) {

                    HomeScreenEnum.VIEW_PAGER_BANNER_VIEW.value -> {
                        item {
                            Log.d(tag, "VIEW_PAGER_BANNER_VIEW : ${pageData.data}")
                            ViewPagerView(pageData.data)
                        }
                    }

                    HomeScreenEnum.SINGLE_BANNER_VIEW.value -> {
                        items(pageData.data) { index ->
                            Log.d(tag, "SINGLE_BANNER_VIEW : ${pageData.data}")
                            SingleBannerViewInsideTitle(index)
                        }
                    }

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
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(
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
private fun HomeScreenPreview() {
    FakePreview(
        fakeData = Unit,
        useUiState = false,
        onSuccess = {
            // ---- Fake ViewModel with instant data ----
            val fakeVM = remember {
                object : HomeViewModel(HomeRepository()) {
                    init {
                        previewInjectData(
                            categories = homeCategoryFakeData,
                            pageData = homePageFakeData,
                            exploreData = exploreItemFakeData,
                            bottomBar = true
                        )
                    }
                }
            }

            CompositionLocalProvider(
                LocalBottomBarVisible provides remember { mutableStateOf(true) },
                LocalIsDarkTheme provides remember { mutableStateOf(false) }
            ) {
                HomeScreen(
                    viewModel = fakeVM,
                    onNavigate = { _, _, _, _ -> },
                    navController = rememberNavController(),
                    drawerState = rememberDrawerState(DrawerValue.Closed),
                    onMenuClick = {}
                )
            }
        }
    )
}
