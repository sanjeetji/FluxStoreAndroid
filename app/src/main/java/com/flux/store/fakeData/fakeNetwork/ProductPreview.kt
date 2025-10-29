package com.flux.store.fakeData.fakeNetwork

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flux.store.model.response.BaseResponse
import com.flux.store.model.response.ProductDetailsResponse
import com.flux.store.ui.screens.composeHelper.ErrorView
import com.flux.store.ui.screens.composeHelper.LoadingView
import com.flux.store.viewmodel.UiState

@Preview(showBackground = true)
@Composable
fun ProductPreview() {
//    val fakeProduct = ProductDetailsResponse(
//        productId = 1,
//        productName = "Flux Wireless Earbuds",
//        price = 79.99,
//        description = "Experience powerful sound and all-day comfort."
//    )

    val viewModel = remember { FakeViewModel<ProductDetailsResponse>() }

    // trigger fake data load only once
    LaunchedEffect(Unit) {
//        viewModel.loadFakeData(fakeProduct)
    }

    val state by viewModel.uiState.collectAsState()

    when (state) {
        UiState.Loading -> LoadingView()
        is UiState.Error -> ErrorView(message = (state as UiState.Error).message) {}
        is UiState.Success -> {
            val product = (state as UiState.Success<BaseResponse<ProductDetailsResponse>>).data.data
//            ProductCard(product)
        }
    }
}

@Composable
fun ProductCard(product: ProductDetailsResponse) {
    Column(Modifier.padding(16.dp)) {
        Text(product.productName, style = MaterialTheme.typography.titleLarge)
//        Text("Price: $${product.price}", style = MaterialTheme.typography.bodyLarge)
        Text(product.description, style = MaterialTheme.typography.bodyMedium)
    }
}
