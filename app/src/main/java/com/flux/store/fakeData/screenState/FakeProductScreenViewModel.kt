package com.flux.store.fakeData.screenState

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flux.store.network.NetworkResult
import com.flux.store.viewmodel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FakeProductScreenViewModel @Inject constructor(
    private val repository: FakeProductRepository,
    private val reviewRepository: FakeReviewRepository
) : ViewModel() {

    // one unified state for the whole screen
    private val _screenState = MutableStateFlow(ProductScreenState())
    val screenState: StateFlow<ProductScreenState> = _screenState

    // 🔹 product details API
    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            repository.getProductDetails(productId).collect { result ->
                _screenState.value = when (result) {
                    is NetworkResult.Loading -> _screenState.value.copy(productDetails = UiState.Loading)
                    is NetworkResult.Success -> _screenState.value.copy(productDetails = UiState.Success(result.data))
                    is NetworkResult.Error -> _screenState.value.copy(productDetails = UiState.Error(result.message))
                }
            }
        }
    }

    // 🔹 similar products API
    fun getSimilarProducts(categoryId: Int) {
        viewModelScope.launch {
            repository.getSimilarProducts(categoryId).collect { result ->
                _screenState.value = when (result) {
                    is NetworkResult.Loading -> _screenState.value.copy(similarProducts = UiState.Loading)
                    is NetworkResult.Success -> _screenState.value.copy(similarProducts = UiState.Success(result.data))
                    is NetworkResult.Error -> _screenState.value.copy(similarProducts = UiState.Error(result.message))
                }
            }
        }
    }

    // 🔹 product reviews API
    fun getProductReviews(productId: Int) {
        viewModelScope.launch {
            reviewRepository.getProductReviews(productId).collect { result ->
                _screenState.value = when (result) {
                    is NetworkResult.Loading -> _screenState.value.copy(reviews = UiState.Loading)
                    is NetworkResult.Success -> _screenState.value.copy(reviews = UiState.Success(result.data))
                    is NetworkResult.Error -> _screenState.value.copy(reviews = UiState.Error(result.message))
                }
            }
        }
    }
}
