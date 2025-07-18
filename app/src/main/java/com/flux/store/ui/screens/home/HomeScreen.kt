package com.flux.store.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.flux.store.R
import com.flux.store.helper.HomeScreenEnum
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.helper.rememberNavItems
import com.flux.store.model.response.HomeBanner
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.repository.HomeRepository
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
) {
    val scope = rememberCoroutineScope()
    BackHandler {
        navController.popBackStack()
    }
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                // use surface so it’s a neutral panel
                drawerContainerColor = MaterialTheme.colorScheme.primary,
                // and onSurface for any text/icons inside
                drawerContentColor = MaterialTheme.colorScheme.onPrimary,
                drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp),
                modifier = Modifier
                    .fillMaxHeight()          // ← full vertical coverage
                    .fillMaxWidth(0.7f)       // ← half the screen width
            ) {
                Row(
                    Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painterResource(R.drawable.img_female_avatar),
                        contentDescription = "Profile image",
                        modifier = Modifier.size(60.dp)
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    onNavigate(
                                        LoginRoutes.ForgotPasswordScreen.toRoute(), "", null, false
                                    )
                                }
                                .fillMaxWidth(),
                            text = tr(R.string.user_name),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    onNavigate(
                                        LoginRoutes.ForgotPasswordScreen.toRoute(),
                                        "",
                                        null,
                                        false
                                    )
                                }
                                .fillMaxWidth(),
                            text = tr(R.string.user_email),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(Modifier.height(72.dp))
                val items = rememberNavItems(scope, drawerState, onNavigate)
                val isDarkState = LocalIsDarkTheme.current

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 4.dp)
                ) {
                    // Main items
                    items.take(4).forEachIndexed { index, item ->
                        item {
                            DrawerItem(icon = item.icon, label = item.label, onClick = item.onClick)
                        }
                    }

                    // Section header
                    item {
                        Spacer(Modifier.height(24.dp))
                        Text(
                            tr(R.string.other),
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 54.dp, vertical = 8.dp)
                        )
                        Spacer(Modifier.height(24.dp))
                    }

                    // Other items
                    items.drop(4).forEachIndexed { _, item ->
                        item {
                            DrawerItem(icon = item.icon, label = item.label, onClick = item.onClick)
                        }
                    }

                    // Push the toggle to the bottom
                    item {
                        Spacer(Modifier.height(32.dp))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(37.dp)
                                .padding(start = 22.dp, end = 22.dp)
                                .clip(shape = RoundedCornerShape(40))
                                .background(color = MaterialTheme.colorScheme.secondary),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Text(
                                "🌞 Light",
                                modifier =
                                    Modifier
                                        .clickable {
                                            isDarkState.value = false
                                        }
                                        .align(Alignment.CenterVertically)
                                        .fillMaxWidth(0.5f)
                                        .height(33.dp)
                                        .clip(shape = RoundedCornerShape(40))
                                        .background(if (isDarkState.value) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary)
                                        .padding(start = 15.dp, top = 8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkState.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                "🌜 Dark",
                                modifier = Modifier
                                    .clickable {
                                        isDarkState.value = true
                                    }
                                    .align(Alignment.CenterVertically)
                                    .fillMaxWidth(1f)
                                    .height(33.dp)
                                    .clip(shape = RoundedCornerShape(40))
                                    .background(if (isDarkState.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                                    .padding(start = 15.dp, top = 8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkState.value) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            containerColor = MaterialTheme.colorScheme.primary,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 1.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Open drawer",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { scope.launch { drawerState.open() } }
                    )
                    Text(
                        text = tr(R.string.app_name1),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Image(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification"
                    )
                }
            },
            bottomBar = {
                viewModel.setShowBottomBar(true)
                val showBar by viewModel.showBottomBar.collectAsState()
                val bottomBarVisible = LocalBottomBarVisible.current
                LaunchedEffect(Unit) { bottomBarVisible.value = showBar }
            }
        ) { innerPadding ->
            HomeContent(
                viewModel = viewModel,
                onNavigate = onNavigate,
                navController = navController,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                drawerState = drawerState
            )
        }
    }
}

@Composable
fun DrawerItem(icon: Int, label: String, onClick: () -> Unit) {
    Row(
        Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(icon),
            contentDescription = label,
            modifier = Modifier.size(22.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(Modifier.width(16.dp))
        Text(
            label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    onNavigate: (String, Any?, String?, Boolean) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    drawerState: DrawerState
) {

    val context = LocalContext.current
    val categoryData = viewModel.categoryData.collectAsState().value
    val homeBannerData = viewModel.homeBannerData.collectAsState().value
    var selectedIndex by remember { mutableIntStateOf(0) }
    val tag = "HomeScreen"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {

        Spacer(modifier = Modifier.height(46.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            itemsIndexed(categoryData) { index, category ->
                val isSelected = index == selectedIndex
                Column(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
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
                            .size(36.dp)
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
                            modifier = Modifier.size(26.dp),
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
                when(pageData.viewType){
                    HomeScreenEnum.VIEW_PAGER_BANNER_VIEW.value ->{
                        item {
                            Log.d(tag, "VIEW_PAGER_BANNER_VIEW : ${pageData.data}")
                            ViewPagerView(pageData.data)
                        }
                    }
                    HomeScreenEnum.SINGLE_BANNER_VIEW.value ->{
                        items(pageData.data) { index ->
                            Log.d(tag, "SINGLE_BANNER_VIEW : ${pageData.data}")
                            SingleBannerView(index)
                        }
                    }
                    HomeScreenEnum.LARGE_TALL_BANNER_LIST_VIEW.value ->{
                        item {
                            Log.d(tag, "LARGE_TALL_BANNER_LIST_VIEW : ${pageData.data}")
                        }
                    }
                    HomeScreenEnum.SMALL_WIDE_BANNER_LIST_VIEW.value ->{
                        item {
                            Log.d(tag, "SMALL_WIDE_BANNER_LIST_VIEW : ${pageData.data}")
                        }
                    }
                    HomeScreenEnum.LARGE_WIDE_BANNER_LIST_VIEW.value ->{
                        item {
                            Log.d(tag, "LARGE_WIDE_BANNER_LIST_VIEW : ${pageData.data}")
                        }
                    }
                    HomeScreenEnum.SMALL_TALL_BANNER_LIST_VIEW.value ->{
                        item {
                            Log.d(tag, "SMALL_TALL_BANNER_LIST_VIEW : ${pageData.data}")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerView(viewPagerData: List<HomeBanner>) {
    // Create a PagerState only for this banner list:
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
            modifier = Modifier.fillMaxWidth().height(170.dp)            // match your image height
        ){
            AsyncImage(
                model = viewPagerData[page].bannerImage,
                contentDescription = "Intro Image ${page + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            )
            Text(text = viewPagerData[page].bannerTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                minLines = 2,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding( top = 40.dp, end = 16.dp)
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

@Composable
fun SingleBannerView(banner: HomeBanner) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model              = banner.bannerImage,
            contentDescription = banner.bannerTitle,
            modifier           = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.height(8.dp))
        Text(
            banner.bannerTitle,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 4.dp)
        )
        banner.bannerDescription?.let {
            Text(
                it,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeAppTheme(false) {
        CompositionLocalProvider(
            LocalBottomBarVisible provides remember { mutableStateOf(true) },
            LocalIsDarkTheme provides remember { mutableStateOf(false) },
        ) {
            val previewVM = remember { HomeViewModel(HomeRepository()) }
            HomeScreen(
                viewModel = previewVM,
                onNavigate = { route, _, _, _ -> },
                navController = rememberNavController(),
                drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            )
        }
    }
}
