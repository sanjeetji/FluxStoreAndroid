package com.flux.store.fakeData.fakeNetwork

import com.flux.store.model.response.BaseResponse
import com.flux.store.network.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository {

    /**
     * Simulates a fake network call returning BaseResponse<T>.
     * Use it for previews or dummy UI states.
     */
    fun <T> getFakeData(
        fakeData: T,
        flag: Int = 200,
        message: String = "Success",
        delayMs: Long = 800L
    ): Flow<NetworkResult<BaseResponse<T>>> = flow {
        emit(NetworkResult.Loading)
        delay(delayMs) // simulate network delay
        emit(NetworkResult.Success(BaseResponse(flag, message, fakeData)))
    }

    /**
     * Simulates an error state.
     */
    fun <T> getFakeError(message: String = "Something went wrong"): Flow<NetworkResult<BaseResponse<T>>> = flow {
        emit(NetworkResult.Loading)
        delay(600)
        emit(NetworkResult.Error(message))
    }
}
