package com.flux.store.fakeData.screenState

import com.flux.store.model.response.BaseResponse
import com.flux.store.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeReviewRepository @Inject constructor() {


    fun getProductReviews(productId: Int): Flow<NetworkResult<BaseResponse<FakeReviewResponse>>> =
        flow {
            emit(NetworkResult.Loading)
//            emit(caller.safeApiCall { api.getProductReviews(productId) })
        }
}