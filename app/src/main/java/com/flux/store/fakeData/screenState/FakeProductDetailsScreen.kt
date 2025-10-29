import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.flux.store.fakeData.screenState.FakeProduct
import com.flux.store.fakeData.screenState.FakeProductDetailsResponse
import com.flux.store.fakeData.screenState.FakeProductScreenViewModel
import com.flux.store.fakeData.screenState.FakeReviewResponse
import com.flux.store.model.response.BaseResponse
import com.flux.store.ui.screens.composeHelper.ErrorView
import com.flux.store.ui.screens.composeHelper.LoadingView
import com.flux.store.viewmodel.UiState

@Composable
fun ProductScreen(
    viewModel: FakeProductScreenViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.screenState.collectAsState()

    // --- Product Details ---
    when (state.productDetails) {
        UiState.Loading -> LoadingView()
        is UiState.Error -> ErrorView(
            message = (state.productDetails as UiState.Error).message,
            onBack = onBack
        )
        is UiState.Success -> {
            val product = (state.productDetails as UiState.Success<BaseResponse<FakeProductDetailsResponse>>).data.data
//            if (product != null) ProductHeaderSection(product)//Design the Product Header Section View/Screen
        }
    }

    // --- Similar Products ---
    when (state.similarProducts) {
        UiState.Loading -> Unit // optional shimmer
        is UiState.Error -> Text("Couldn't load similar products")
        is UiState.Success -> {
            val list = (state.similarProducts as UiState.Success<BaseResponse<List<FakeProduct>>>).data.data
//            if (!list.isNullOrEmpty()) SimilarProductsSection(list)//Design the Product List section View/Screen
        }
    }

    // --- Reviews ---
    when (state.reviews) {
        UiState.Loading -> Unit
        is UiState.Error -> Text("Couldn't load reviews")
        is UiState.Success -> {
            val reviews = (state.reviews as UiState.Success<BaseResponse<FakeReviewResponse>>).data.data
//            if (reviews != null) ReviewsSection(reviews)//Design the Product Review Section View/Screen
        }
    }
}
