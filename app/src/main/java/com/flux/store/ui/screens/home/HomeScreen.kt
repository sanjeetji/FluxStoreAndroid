package com.flux.store.ui.screens.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.flux.store.R
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.helper.rememberNavItems
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.ui.theme.ThemeOptionBackground
import com.flux.store.ui.theme.VeryDarkGrayColor
import com.flux.store.viewmodel.HomeViewModel
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
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                // use surface so it’s a neutral panel
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                // and onSurface for any text/icons inside
                drawerContentColor   = MaterialTheme.colorScheme.onSurface,
                drawerShape = RoundedCornerShape(topEnd=32.dp, bottomEnd=32.dp),
                modifier    = Modifier
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
                    Column(modifier = Modifier
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
                            color = Black
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
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(Modifier.height(72.dp))
                val items = rememberNavItems(scope, drawerState, onNavigate)
                val isDarkState = LocalIsDarkTheme.current

                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 4.dp)) {
                    // Main items
                    items.take(4).forEachIndexed { index, item ->
                        item {
                            DrawerItem(icon=item.icon, label=item.label, onClick=item.onClick)
                        }
                    }

                    // Section header
                    item {
                        Spacer(Modifier.height(24.dp))
                        Text(tr(R.string.other),
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Start,
                            color = VeryDarkGrayColor,
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
                            DrawerItem(icon=item.icon, label=item.label, onClick=item.onClick)
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
                                .background(color = ThemeOptionBackground),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Row(
                                Modifier
                                    .clickable{
                                        isDarkState.value = false
                                    }
                                    .fillMaxWidth(0.5f)
                                    .height(35.dp)
                                    .padding(start = 4.dp, end = 2.dp)
                                    .clip(shape = RoundedCornerShape(40))
                                    .background(
                                        color = if(isDarkState.value) ThemeOptionBackground else White
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text("🌞 Light",
                                    modifier =
                                    Modifier.fillMaxWidth()
                                    .padding(start = 12.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Black
                                )
                            }

                            Spacer(modifier = Modifier.width(5.dp))

                            Row(
                                Modifier
                                    .clickable{
                                        isDarkState.value = true
                                    }
                                    .fillMaxWidth(1f)
                                    .height(35.dp)
                                    .padding(start = 4.dp, end = 2.dp)
                                    .clip(shape = RoundedCornerShape(40))
                                    .background(
                                        color = if(isDarkState.value) White else ThemeOptionBackground
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    "🌜 Dark",
                                    modifier = Modifier.fillMaxWidth()
                                    .padding(start = 12.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = VeryDarkGrayColor
                                )
                            }

                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Open drawer",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = {
                viewModel.setShowBottomBar(true)
                val showBar by viewModel.showBottomBar.collectAsState()
                val bottomBarVisible = LocalBottomBarVisible.current
                LaunchedEffect(Unit) { bottomBarVisible.value = showBar }
            }
        ) { innerPadding ->
            HomeContent(
                viewModel    = viewModel,
                onNavigate   = onNavigate,
                navController = navController,
                modifier     = Modifier.padding(innerPadding).fillMaxSize(),
                drawerState = drawerState
            )
        }
    }
}

@Composable
fun DrawerItem(icon: Int, label: String, onClick: ()->Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(icon),
            contentDescription = label,
            modifier = Modifier.size(22.dp),
            tint = VeryDarkGrayColor
        )
        Spacer(Modifier.width(16.dp))
        Text(label,
            style = MaterialTheme.typography.titleSmall,
            color = VeryDarkGrayColor
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
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen", style = MaterialTheme.typography.headlineMedium)
        // … rest of your buttons, state, etc …
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val fakeBottomBarState = remember { mutableStateOf(true) }
    val openDrawerState = rememberDrawerState(initialValue = DrawerValue.Open) // ← OPEN
    CompositionLocalProvider(
        LocalBottomBarVisible provides fakeBottomBarState
    ) {
        ComposeAppTheme {
            HomeScreen(
                viewModel = HomeViewModel(),
                onNavigate = { route, _, _, _ -> },
                navController = rememberNavController(),
                drawerState  = openDrawerState
            )
        }
    }
    
}