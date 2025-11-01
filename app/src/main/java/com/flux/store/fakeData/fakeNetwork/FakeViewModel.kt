package com.flux.store.fakeData.fakeNetwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flux.store.model.response.BaseResponse
import com.flux.store.network.NetworkResult
import com.flux.store.viewmodel.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FakeViewModel<T>(
    private val repository: FakeRepository = FakeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<BaseResponse<T>>>(UiState.Loading)
    val uiState: StateFlow<UiState<BaseResponse<T>>> = _uiState

    private var loadJob: Job? = null

    /** Normal runtime – uses coroutines */
    fun loadFakeData(fakeData: T, delayMs: Long = 800L) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            repository.getFakeData(fakeData, delayMs = delayMs).collectLatest {
                _uiState.value = it.toUiState()
            }
        }
    }

    fun loadFakeError(message: String = "Something went wrong", delayMs: Long = 600L) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            repository.getFakeError<T>(message, delayMs).collectLatest {
                _uiState.value = it.toUiState()
            }
        }
    }

    // ---------- Instant preview helpers (no coroutines) ----------
    fun previewSuccess(fakeData: T) {
        _uiState.value = UiState.Success(BaseResponse(200, "Success", fakeData))
    }

    fun previewError(message: String = "Preview error") {
        _uiState.value = UiState.Error(message)
    }
}

/** Mapper from NetworkResult → UiState */
private fun <T> NetworkResult<BaseResponse<T>>.toUiState(): UiState<BaseResponse<T>> =
    when (this) {
        is NetworkResult.Loading -> UiState.Loading
        is NetworkResult.Success -> UiState.Success(data)
        is NetworkResult.Error -> UiState.Error(message)
    }