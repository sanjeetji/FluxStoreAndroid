package com.flux.store.fakeData.screenState

import com.flux.store.model.response.BaseResponse
import com.flux.store.network.ApiCaller
import com.flux.store.network.ApiService
import com.flux.store.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeProductRepository @Inject constructor(
    private val api: ApiService,
    private val caller: ApiCaller
) {

    fun getProductDetails(productId: Int): Flow<NetworkResult<BaseResponse<FakeProductDetailsResponse>>> =
        flow {
            emit(NetworkResult.Loading)
//            emit(caller.safeApiCall { api.getProductDetails(productId) })
        }

    fun getSimilarProducts(productId: Int): Flow<NetworkResult<BaseResponse<List<FakeProduct>>>> =
        flow {
            emit(NetworkResult.Loading)
//            emit(caller.safeApiCall { api.getSimilarProducts(productId) })
        }

}