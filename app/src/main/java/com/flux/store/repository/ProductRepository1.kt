package com.flux.store.repository

import com.flux.store.model.response.BaseResponse
import com.flux.store.model.response.ProductDetailsResponse
import com.flux.store.network.ApiCaller
import com.flux.store.network.ApiService
import com.flux.store.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository1 @Inject constructor(
    private val api: ApiService,
    private val caller: ApiCaller
) {
    fun getProductDetails(productId: Int): Flow<NetworkResult<BaseResponse<ProductDetailsResponse>>> = flow {
        emit(NetworkResult.Loading)
        emit(caller.safeApiCall { api.getProductDetails(productId) })
    }.catch { e ->
        emit(NetworkResult.Error(e.localizedMessage ?: "Network error"))
    }.flowOn(Dispatchers.IO)
}

