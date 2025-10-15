package com.flux.store.ui.screens.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flux.store.R
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.helper.rememberNavItems
import com.flux.store.navigation.bottomNav.BottomBar
import com.flux.store.navigation.routes.LoginRoutes
import com.flux.store.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenScaffold(
    viewModel: HomeViewModel,
    navController: NavHostController,
    titleResId: Int,
    onMenuClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    drawerState: DrawerState,
    content: @Composable (Modifier) -> Unit
) {
    val scope = rememberCoroutineScope()
    val bottomBarVisible = LocalBottomBarVisible.current
    val showBottomBar by viewModel.showBottomBar.collectAsState()

    // Sync LocalBottomBarVisible with viewModel.showBottomBar
    LaunchedEffect(showBottomBar) {
        bottomBarVisible.value = showBottomBar
    }

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary,
                drawerContentColor = MaterialTheme.colorScheme.onPrimary,
                drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f)
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
                    items.take(4).forEachIndexed { index, item ->
                        item {
                            MyDrawerItem(icon = item.icon, label = item.label,
                                onClick = item.onClick
                            )
                        }
                    }
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
                    items.drop(4).forEachIndexed { _, item ->
                        item {
                            MyDrawerItem(icon = item.icon, label = item.label, onClick = item.onClick)
                        }
                    }
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
                                modifier = Modifier
                                    .clickable { isDarkState.value = false }
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
                                    .clickable { isDarkState.value = true }
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
                        text = tr(titleResId),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onNotificationClick() }
                    )
                }
            },
            bottomBar = {
                if (bottomBarVisible.value) {
                    BottomBar(navController)
                }
            }
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun MyDrawerItem(icon: Int, label: String, onClick: () -> Unit) {
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