package com.flux.store.fakeData.fakeNetwork

import com.flux.store.model.response.BaseResponse
import com.flux.store.network.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class FakeRepository {

    /** Simulate a successful network call */
    fun <T> getFakeData(
        fakeData: T,
        flag: Int = 200,
        message: String = "Success",
        delayMs: Long = 800L
    ): Flow<NetworkResult<BaseResponse<T>>> = flow {
        emit(NetworkResult.Loading)
        delay(delayMs)
        emit(NetworkResult.Success(BaseResponse(flag, message, fakeData)))
    }.onStart { emit(NetworkResult.Loading) }
        .catch { emit(NetworkResult.Error(it.message ?: "Something went wrong")) }

    /** Simulate an error */
    fun <T> getFakeError(
        message: String = "Something went wrong",
        delayMs: Long = 600L
    ): Flow<NetworkResult<BaseResponse<T>>> = flow {
        emit(NetworkResult.Loading)
        delay(delayMs)
        emit(NetworkResult.Error(message))
    }.onStart { emit(NetworkResult.Loading) }
}