package com.flux.store.model.response

import androidx.compose.ui.graphics.vector.ImageVector
import com.flux.store.navigation.routes.Routes

data class BottomNavigation(
    val label:String,
    val icon:ImageVector,
    val route: Routes
)