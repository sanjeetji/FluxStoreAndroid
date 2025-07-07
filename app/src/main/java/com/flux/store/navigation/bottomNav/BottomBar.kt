package com.flux.store.navigation.bottomNav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.flux.store.ui.theme.BottomBarUnselectedOptionColor
import com.flux.store.ui.theme.ThemeColor
import com.flux.store.ui.theme.WhiteColor

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = WhiteColor,
        tonalElevation = 0.dp,
        modifier = Modifier
            .height(72.dp)
            .clip(RoundedCornerShape(40.dp)),
        ) {
        BottomNavigationItems.bottomNavigation.forEach { item ->
            val route = item.route.toRoute()
            NavigationBarItem(
                selected = (currentRoute == route),
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = ThemeColor,
                    unselectedIconColor = BottomBarUnselectedOptionColor,
                    selectedTextColor   = ThemeColor,
                    unselectedTextColor = BottomBarUnselectedOptionColor,
                    indicatorColor      = Color.Transparent
                ),
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector   = item.icon,
                            contentDescription = item.label,
                            tint          = if (currentRoute == route) ThemeColor else BottomBarUnselectedOptionColor
                        )
                        Text(
                            text   = item.label,
                            style  = MaterialTheme.typography.labelSmall,
                            color  = if (currentRoute == route) ThemeColor else BottomBarUnselectedOptionColor
                        )
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    BottomBar(navController = NavHostController(LocalContext.current))
}

