package com.flux.store.fakeData.fakeNetwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flux.store.model.response.BaseResponse
import com.flux.store.network.NetworkResult
import com.flux.store.viewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FakeViewModel<T>(
    private val repository: FakeRepository = FakeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<BaseResponse<T>>>(UiState.Loading)
    val uiState: StateFlow<UiState<BaseResponse<T>>> = _uiState

    /**
     * Simulates network success by collecting data from the fake repository.
     */
    fun loadFakeData(fakeData: T) {
        viewModelScope.launch {
            repository.getFakeData(fakeData).collect { result ->
                _uiState.value = when (result) {
                    is NetworkResult.Loading -> UiState.Loading
                    is NetworkResult.Success -> UiState.Success(result.data)
                    is NetworkResult.Error -> UiState.Error(result.message)
                }
            }
        }
    }

    /**
     * Simulates network error for testing error UI states.
     */
    fun loadFakeError(message: String = "Something went wrong") {
        viewModelScope.launch {
            repository.getFakeError<T>(message).collect { result ->
                _uiState.value = when (result) {
                    is NetworkResult.Loading -> UiState.Loading
                    is NetworkResult.Success -> UiState.Success(result.data)
                    is NetworkResult.Error -> UiState.Error(result.message)
                }
            }
        }
    }

    // ---------------------------------------------------------
    // 🔹 These helpers make Compose Previews work instantly
    // ---------------------------------------------------------

    /**
     * Instantly sets UiState.Success without using coroutines.
     * Use this only in @Preview mode to render static success UI.
     */
    fun previewSuccess(fakeData: T) {
        _uiState.value = UiState.Success(BaseResponse(200, "Success", fakeData))
    }

    /**
     * Instantly sets UiState.Error without using coroutines.
     * Use this only in @Preview mode to render error UI.
     */
    fun previewError(message: String = "Something went wrong") {
        _uiState.value = UiState.Error(message)
    }
}
