package com.flux.store.ui.screens.productDetail

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.flux.store.R
import com.flux.store.fakeData.fakeNetwork.FakePreview
import com.flux.store.fakeData.fakeNetwork.exploreItemFakeData
import com.flux.store.fakeData.fakeNetwork.homeCategoryFakeData
import com.flux.store.fakeData.fakeNetwork.homePageFakeData
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.model.response.BannerCategory
import com.flux.store.model.response.BannerData
import com.flux.store.navigation.routes.ProductDetailsRoutes
import com.flux.store.repository.ProductRepository
import com.flux.store.ui.screens.dynamicHelperView.StarRatingBar
import com.flux.store.viewmodel.ProductListViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController,
) {
    val context = LocalContext.current
//    viewModel.setShowBottomBar(true)
    val inPreview = LocalInspectionMode.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val itemWidth = screenWidth * 0.46f
    BackHandler {
        navController.popBackStack()
    }

    // Retrieve or use dummy payload for preview
    val categoryData: BannerCategory? = if (inPreview) {
        BannerCategory(
            categoryId = 1,
            categoryName = "Sample Category",
            totalItem = 4,
            bannerCategory = listOf(
                BannerData(1, R.drawable.img_girl_1, "Girl Dress One", 12.0, 1.0, 4, 54.0, false),
                BannerData(2, R.drawable.img_girl_1, "Girl Dress Two", 15.0, 2.0, 5, 120.0, true),
                BannerData(3, R.drawable.img_girl_1, "Girl Dress Three", 18.0, 0.0, 3, 89.0, false),
                BannerData(4, R.drawable.img_girl_1, "Girl Dress Four", 22.0, 3.0, 4, 67.0, true)
            )
        )
    } else {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get("navPayload")
    }

    // Optional: Clean up after retrieval to prevent stale data (skip in preview)
    if (!inPreview) {
        navController.previousBackStackEntry?.savedStateHandle?.remove<BannerCategory>("navPayload")
    }

    // Example: Trigger ViewModel fetch when category loads (null-safe)
    /*LaunchedEffect(categoryData?.categoryName) {
        categoryData?.categoryName?.let { name ->
            viewModel.fetchProductsByCategory(name)  // Assuming you add this method to your VM
        }
    }*/


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.Left,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "back button",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .shadow(1.dp, shape = RoundedCornerShape(50.dp), clip = true)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = categoryData?.categoryName ?: "Category Name",  // Dynamic from payload
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(
                text = "Found ${categoryData?.totalItem ?: 150} Results",  // Dynamic from payload
                style = MaterialTheme.typography.bodySmall,
            )
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(60.dp))
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(60.dp))
                    .clickable{
                        Toast.makeText(context,"Filter Btn is Clicked.", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                        .padding(start = 16.dp, end = 12.dp, top = 5.dp, bottom = 5.dp)
                ){
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = "Filter",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown, // Note: Changed from KeyboardArrowUp as per code, but assuming it's correct
                        contentDescription = "Filter icon",  // Improved for accessibility
                        tint = MaterialTheme.colorScheme.onPrimary  // Optional: Theme consistency
                    )
                }
            }
        }

        val isTallBanner = true
        val bannerHeight = if (isTallBanner) 220.dp else 160.dp // Adjust as needed; 'imageHeight' was undefined, so using a default

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(categoryData?.bannerCategory ?: emptyList()) { banner ->
                ProductListItem(
                    banner = banner,
                    bannerHeight = bannerHeight,
                    itemWidth = itemWidth,
                    onNavigate = {
                        // Call the outer navigation lambda *inside* this no-arg lambda.
                        // Pass `banner` as payload (instead of empty string) so the detail screen can access the product data.
                        onNavigate(
                            ProductDetailsRoutes.ProductDetailScreen.toRoute(),
                            banner,  // Use the actual banner data for the detail screen.
                            ProductDetailsRoutes.ProductListScreen.toRoute(),
                            false
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun ProductListItem(
    banner: BannerData,
    bannerHeight: Dp,
    itemWidth: Dp,
    onNavigate: () -> Unit
) {
    val inPreview = LocalInspectionMode.current
    val context = LocalContext.current
    var isFavourite by remember { mutableStateOf(banner.isFavourite) }
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(bannerHeight)
        .clip(RoundedCornerShape(12.dp))

    Column(
        modifier = Modifier
            .width(itemWidth)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {

        Box(
            modifier = Modifier.clickable{
                onNavigate()
            }
        ) {
            if (inPreview || banner.image is Int) {
                val resId = banner.image as? Int ?: R.drawable.img_home_banner
                Image(
                    painter = painterResource(resId),
                    contentDescription = banner.name,
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                )
            } else {
                AsyncImage(
                    model = banner.image,
                    contentDescription = banner.name,
                    placeholder = painterResource(R.drawable.img_home_banner),
                    error = painterResource(R.drawable.img_home_banner),
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                )
            }

            // Small favourite icon (replacing IconButton)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 12.dp)
                    .size(28.dp) // 👈 total bubble size (small)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        isFavourite = !isFavourite
                        val msg = if (isFavourite) "Added to favourites" else "Removed from favourites"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavourite) "Unfavourite" else "Favourite",
                    tint = if (isFavourite) Color.Red else MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(16.dp) // 👈 icon size
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Increased from 1.dp for better spacing

        Text(
            text = banner.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )

        // Fare Row
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            banner.originalFare?.let { original ->
                Text(
                    text = "$${original}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Red,
                    style = MaterialTheme.typography.labelSmall.copy(
                        lineBreak = LineBreak.Paragraph,
                        hyphens = Hyphens.Auto
                    ).copy(textDecoration = if (banner.discountedFare != null) TextDecoration.LineThrough else null),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 4.dp),
                    softWrap = true
                )
                if (banner.discountedFare != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            banner.discountedFare?.let { discounted ->
                Text(
                    text = "$${discounted}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    style = MaterialTheme.typography.labelSmall.copy(
                        lineBreak = LineBreak.Paragraph,
                        hyphens = Hyphens.Auto
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 4.dp),
                    softWrap = true
                )
            }
        }

        // Rating Row
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            banner.averageRating?.let {
                Log.d("Rating","Ratting is ${it}")
                StarRatingBar(rating = it.coerceIn(0.0, 5.0)) // Coerce to 0-5 range
                Spacer(modifier = Modifier.width(4.dp))
                banner.totalRating?.let { total ->
                    Text(
                        text = "($total)",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Home – Light")
@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Home – Dark")
@Composable
private fun ProductListScreenPreview() {
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
                ProductListScreen(
                    viewModel = fakeVM,
                    onNavigate = { route, _, _, _ -> println("Navigating to $route") },
                    navController = rememberNavController(),
                )
            }
        }
    )
}