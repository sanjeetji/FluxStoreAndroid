package com.flux.store.ui.screens.productDetail

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.flux.store.R
import com.flux.store.fakeData.fakeNetwork.FakeViewModel
import com.flux.store.fakeData.fakeNetwork.productDetailsData
import com.flux.store.helper.LocalBottomBarVisible
import com.flux.store.helper.LocalIsDarkTheme
import com.flux.store.helper.localizationHelper.tr
import com.flux.store.model.response.BaseResponse
import com.flux.store.model.response.ProductDetailsResponse
import com.flux.store.repository.ProductRepository
import com.flux.store.ui.screens.composeHelper.ErrorView
import com.flux.store.ui.screens.composeHelper.LoadingView
import com.flux.store.ui.screens.dynamicHelperView.StarRatingBar
import com.flux.store.ui.screens.dynamicHelperView.VerticalViewPagerView
import com.flux.store.ui.theme.ComposeAppTheme
import com.flux.store.viewmodel.ProductListViewModel
import com.flux.store.viewmodel.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    viewModel: ProductListViewModel,
    onNavigate: (route: String, payload: Any?, popUpToRoute: String?, inclusive: Boolean) -> Unit,
    navController: NavHostController
) {

    val uiState by viewModel.uiStateForProductDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProductDetailsDummy(1)
    }

    when(uiState){
        is UiState.Loading -> LoadingView("Fetching product details....")
        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            ErrorView(message = message, onBack = {navController.popBackStack()})
        }
        is UiState.Success -> {
            val response = (uiState as UiState.Success<BaseResponse<ProductDetailsResponse>>).data
            val product = response.data

            if (product != null) {
                ProductDetailsContent(product,navController)
            } else {
                ErrorView(message = "No product data available",
                    onBack = {navController.popBackStack()}
                )
            }
        }
    }


   /* val productDetails = viewModel.productDetailsResponse.collectAsState().value
    if (productDetails == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading product details...", color = MaterialTheme.colorScheme.onBackground)
        }
        return
    }*/

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsContent(productDetails: ProductDetailsResponse, navController: NavHostController) {

    var isFavourite by remember { mutableStateOf(productDetails.isFavourite) }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var selectedColor by remember { mutableStateOf<String?>(null) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    var isReviewsExpanded by remember { mutableStateOf(false) }

    val topBarHeight = 56.dp
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val viewPagerHeight = 450.dp
    val peekHeight = (screenHeight - viewPagerHeight).coerceAtLeast(56.dp)

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val density = LocalDensity.current

    val screenHeightPx = with(density) { screenHeight.toPx() }
    val peekHeightPx = with(density) { peekHeight.toPx() }

    // ✅ Initialize to collapsed offset so expandProgress starts at 0 (ViewPager visible)
    var sheetOffsetPx by remember(screenHeightPx, peekHeightPx) {
        mutableStateOf(screenHeightPx - peekHeightPx)
    }

    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.requireOffset() }
            .collect { offset -> sheetOffsetPx = offset }
    }

    // Expansion progress between 0 and 1
    val expandProgress by remember {
        derivedStateOf {
            1f - (sheetOffsetPx / (screenHeightPx - peekHeightPx)).coerceIn(0f, 1f)
        }
    }

    // Cross-fade after 90%
    val fadeThreshold = 0.9f
    val viewPagerAlpha by animateFloatAsState(
        targetValue = if (expandProgress < fadeThreshold)
            1f
        else
            1f - ((expandProgress - fadeThreshold) / (1f - fadeThreshold)).coerceIn(0f, 1f),
        label = "ViewPagerAlpha"
    )
    val topBarAlpha by animateFloatAsState(
        targetValue = if (expandProgress < fadeThreshold)
            0f
        else
            ((expandProgress - fadeThreshold) / (1f - fadeThreshold)).coerceIn(0f, 1f),
        label = "TopBarAlpha"
    )

    BackHandler {
        if (sheetState.currentValue == SheetValue.Expanded) {
            scope.launch { sheetState.partialExpand() }
        } else {
            navController.popBackStack()
        }
    }

    // Root layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // 2️⃣ TopBar fixed overlay (fades in after 90%)
        val topColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
        val bottomColor = MaterialTheme.colorScheme.background.copy(alpha = 0f)
        if (topBarAlpha > 0f) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topBarHeight)
                    .drawBehind {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(topColor, bottomColor)
                            )
                        )
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .alpha(topBarAlpha)
                    .align(Alignment.TopCenter)
                    .zIndex(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = productDetails.productName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(48.dp))
            }
        }

        // 3️⃣ Bottom Sheet (draws above ViewPager, below TopBar)
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = peekHeight,
            sheetContainerColor = MaterialTheme.colorScheme.surface, // solid sheet; make .copy(alpha=.97f) for translucency
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
            sheetContent = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                ) {
                    item {
                        // Optional: keep a tiny spacer so content doesn't feel cramped at image edge
                        Spacer(Modifier.height(8.dp))

                        // Product header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = productDetails.productName,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                productDetails.originalPrice?.let {
                                    Text(
                                        text = "$$it",
                                        color = Color.Red,
                                        textDecoration = TextDecoration.LineThrough,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(Modifier.width(6.dp))
                                }
                                Text(
                                    text = "$${productDetails.discountedPrice}",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            StarRatingBar(rating = productDetails.averageRating.coerceIn(0.0, 5.0))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "(${productDetails.totalRating})",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        Divider(Modifier.padding(vertical = 8.dp))

                        // Color & Size
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(tr(R.string.color), color = MaterialTheme.colorScheme.onPrimary)
                                Spacer(Modifier.height(8.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(productDetails.colorList) { color ->
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(getColor(color))
                                                .border(
                                                    2.dp,
                                                    if (selectedColor == color)
                                                        MaterialTheme.colorScheme.onPrimary
                                                    else
                                                        MaterialTheme.colorScheme.primary,
                                                    CircleShape
                                                )
                                                .clickable { selectedColor = color }
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.width(8.dp))
                            Column(Modifier.weight(1f)) {
                                Text(tr(R.string.size), color = MaterialTheme.colorScheme.onPrimary)
                                Spacer(Modifier.height(8.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(productDetails.sizeList) { size ->
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (size == selectedSize)
                                                        MaterialTheme.colorScheme.onPrimary
                                                    else
                                                        MaterialTheme.colorScheme.outline
                                                )
                                                .clickable { selectedSize = size },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                size,
                                                color = if (size == selectedSize)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.onPrimary,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Divider(Modifier.padding(vertical = 8.dp))

                        // Description
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isDescriptionExpanded = !isDescriptionExpanded }
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    tr(R.string.description),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Icon(
                                    Icons.Filled.ArrowForwardIos,
                                    null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            if (isDescriptionExpanded)
                                Text(
                                    text = productDetails.description,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 12.sp,
                                    maxLines = 6
                                )
                        }

                        Divider(Modifier.padding(vertical = 8.dp))

                        // Reviews
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .clickable { isReviewsExpanded = !isReviewsExpanded }
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    tr(R.string.reviews),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Icon(
                                    Icons.Filled.ArrowForwardIos,
                                    null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            if (isReviewsExpanded) {
                                Spacer(Modifier.height(8.dp))
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            productDetails.averageRating.toString(),
                                            style = MaterialTheme.typography.headlineLarge,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                        Spacer(Modifier.width(5.dp))
                                        Text(
                                            tr(R.string.out_of_five),
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        StarRatingBar(rating = 5.0)
                                        Text(
                                            productDetails.averageRating.toString(),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }

                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    productDetails.reviewsData.ratingData.entries.forEach { entry ->
                                        KeyValueItem(entry.key, entry.value)
                                    }
                                }
                            }
                        }

                        Divider(Modifier.padding(vertical = 8.dp))
                        Text(tr(R.string.similar_product), color = MaterialTheme.colorScheme.onPrimary)
                        Divider(Modifier.padding(top = 8.dp))
                        Spacer(Modifier.navigationBarsPadding())
                    }
                }
            },
            content = {
                // required even if empty
                // 1️⃣ ViewPager background (fades as sheet expands 90%+)
                VerticalViewPagerView(
                    viewPagerData = productDetails.imgList,
                    onBackClick = { navController.popBackStack() },
                    onFavClick = { isFav -> isFavourite = isFav },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(viewPagerHeight)
                        .alpha(viewPagerAlpha)
                        .align(Alignment.TopCenter)
                        .zIndex(0f)
                )
            }
        )
    }
}

fun getColor(colorHex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorHex))
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}

@Composable
fun KeyValueItem(key: Int, value: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Key: $key",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Value: $value",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun ProductDetailsScreenPreview() {
    ComposeAppTheme(false) {
        CompositionLocalProvider(
            LocalBottomBarVisible provides remember { mutableStateOf(true) },
            LocalIsDarkTheme provides remember { mutableStateOf(false) }
        ) {
            val showError = false
            val fakeVM = remember { FakeViewModel<ProductDetailsResponse>() }
            if (showError) {
                fakeVM.previewError("Network unreachable")
            } else {
                fakeVM.previewSuccess(productDetailsData)
            }
            val state by fakeVM.uiState.collectAsState()
            when (state) {
                UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView(message = (state as UiState.Error).message) {}
                is UiState.Success -> {
                    val product = (state as UiState.Success<BaseResponse<ProductDetailsResponse>>).data.data
                    ProductDetailsContent(product!!, rememberNavController())
                }
            }
        }
    }
}
