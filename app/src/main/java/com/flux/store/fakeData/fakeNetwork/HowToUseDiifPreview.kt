package com.flux.store.fakeData.fakeNetwork

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.flux.store.ui.screens.composeHelper.ErrorView
import com.flux.store.ui.screens.composeHelper.LoadingView
import com.flux.store.ui.screens.productDetail.ProductDetailsContent

//How to use (any screen / any view)

//A) For ProductDetailsScreen (with ViewModel + UiState)
@Preview(showBackground = true, showSystemUi = true, name = "ProductDetails - Light")
@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "ProductDetails - Dark")
@Composable
private fun ProductDetailsScreenPreview() {
    FakePreview(
        fakeData = productDetailsData,
        darkTheme = false,
        showError = false,
        useUiState = true,
        onLoading = { LoadingView("Loading...") },
        onError = { msg -> ErrorView(msg) {} },
        onSuccess = { product ->
            ProductDetailsContent(product, rememberNavController())
        }
    )
}

//B) For a screen without ViewModel (e.g. a static info screen)
@Preview(showBackground = true, name = "Static Info Screen")
@Composable
private fun StaticScreenPreview() {
    FakePreview(
        fakeData = Unit,
        useUiState = false,
        onSuccess = { InfoScreenContent() }   // 👈 explicitly bind to onSuccess
    )
}


@Composable
fun InfoScreenContent() {
    Column {
        Text(text = "Name : Sanjeet Kumar Prajapti")
        Text(text = "Age : 28")
    }
}

//C) For a small component (like Card or Dialog)
@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    FakePreview(
        fakeData = productDetailsData,
        useUiState = false
    ) { product ->
//        ProductCard(product)
    }
}


