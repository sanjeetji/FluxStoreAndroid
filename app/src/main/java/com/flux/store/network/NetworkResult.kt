package com.flux.store.network

sealed interface NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>
}