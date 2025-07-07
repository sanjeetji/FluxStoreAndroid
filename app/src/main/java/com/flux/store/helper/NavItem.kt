package com.flux.store.helper

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.flux.store.R
import com.flux.store.navigation.routes.HomeRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NavItem(val icon: Int, val label: String, val onClick: ()->Unit)

@Composable
fun rememberNavItems(
  scope: CoroutineScope,
  drawerState: DrawerState,
  onNavigate: (String,Any?,String?,Boolean)->Unit
) = remember {
  listOf(
    // “Main” section
    NavItem(R.drawable.ic_home,     "Homepage")  {
      scope.launch { drawerState.close();
        onNavigate(HomeRoutes.HomeScreen.toRoute(), null, null, false) }
    },
    NavItem(R.drawable.ic_search,   "Discover") {
      scope.launch { drawerState.close() }
    },
    NavItem(R.drawable.ic_order,    "My Order") {
      scope.launch { drawerState.close()
        onNavigate(HomeRoutes.CategoryScreen.toRoute(), null, null, false)
      }
    },
    NavItem(R.drawable.ic_user,     "My Profile"){
      scope.launch { drawerState.close() }
    },

    // we’ll insert a header here for “OTHER”…

    // “Other” section
    NavItem(R.drawable.ic_setting,  "Setting")  {
      scope.launch { drawerState.close() }
    },
    NavItem(R.drawable.ic_support,  "Support")  {
      scope.launch { drawerState.close() }
    },
    NavItem(R.drawable.ic_about,     "About us") {
      scope.launch { drawerState.close() }
    },
  )
}
