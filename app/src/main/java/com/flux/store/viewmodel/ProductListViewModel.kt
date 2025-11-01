package com.flux.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flux.store.fakeData.fakeNetwork.productDetailsData
import com.flux.store.model.response.BaseResponse
import com.flux.store.model.response.Category
import com.flux.store.model.response.ExploreData
import com.flux.store.model.response.HomePageData
import com.flux.store.model.response.ProductDetailsResponse
import com.flux.store.model.response.RecentSearch
import com.flux.store.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ProductListViewModel @Inject constructor(val repository: ProductRepository/*, val repository1: ProductRepository1*/): ViewModel() {

    val recentSearchData : StateFlow<List<RecentSearch>> = repository.recentSearchData
    val productDetailsResponse: StateFlow<ProductDetailsResponse?> = repository.productDetailsResponse

    // ---------- PUBLIC flows that the UI reads ----------
    private val _categoryData = MutableStateFlow<List<Category>>(emptyList())
    val categoryData: StateFlow<List<Category>> = _categoryData

    private val _homeBannerData = MutableStateFlow<List<HomePageData>>(emptyList())
    val homeBannerData: StateFlow<List<HomePageData>> = _homeBannerData

    private val _exploreData = MutableStateFlow<List<ExploreData>>(emptyList())
    val explorePageData: StateFlow<List<ExploreData>> = _exploreData

    private val _uiStateForProductDetails = MutableStateFlow<UiState<BaseResponse<ProductDetailsResponse>>>(UiState.Loading)
    val uiStateForProductDetails: StateFlow<UiState<BaseResponse<ProductDetailsResponse>>> = _uiStateForProductDetails


    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            /*repository1.getProductDetails(productId).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> _uiStateForProductDetails.value = UiState.Loading
                    is NetworkResult.Success -> _uiStateForProductDetails.value = UiState.Success(result.data)
                    is NetworkResult.Error -> _uiStateForProductDetails.value = UiState.Error(result.message)
                }
            }*/
        }
    }

    fun getProductDetailsDummy(productId: Int) {
        viewModelScope.launch {
            _uiStateForProductDetails.value = UiState.Loading
            // Simulate network delay
            delay(3000L)
            try {
                // Return dummy product data wrapped in BaseResponse
                val fakeResponse = BaseResponse(
                    flag = 200,
                    message = "Fetched successfully (dummy data)",
                    data = productDetailsData
                )
                _uiStateForProductDetails.value = UiState.Success(fakeResponse)
            } catch (e: Exception) {
                _uiStateForProductDetails.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    // ---- Helper for preview only (not used in production) ----
    internal fun previewInjectData(
        categories: List<Category>,
        pageData: List<HomePageData>,
        exploreData: List<ExploreData>,
    ) {
        _categoryData.value = categories
        _homeBannerData.value = pageData
        _exploreData.value = exploreData
    }

}