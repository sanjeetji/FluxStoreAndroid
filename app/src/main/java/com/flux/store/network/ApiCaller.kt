package com.flux.store.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.flux.store.helper.AppEvent
import com.flux.store.helper.AppEventBus
import com.flux.store.helper.SessionStore
import com.flux.store.utils.APIResponseFlags
import com.flux.store.utils.Constant
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiCaller @Inject constructor(
    private val sessionStore: SessionStore,
    private val eventBus: AppEventBus
) {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response: Response<T> = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("ApiCaller", "✅ ${response.code()}: ${Gson().toJson(body)}")

                    val flag = extractFlag(body)
                    if (flag != null && isAuthFlag(flag)) handleAuthFlag(flag)

                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error("Empty response body", response.code())
                }
            } else {
                val raw = response.errorBody()?.string()
                Log.e("ApiCaller", "❌ ${response.code()} ${response.message()}: $raw")
                if (response.code() == 401) handleAuthFlag(APIResponseFlags.UN_AUTHORIZE)
                NetworkResult.Error(raw ?: response.message(), response.code())
            }
        } catch (e: Exception) {
            Log.e("ApiCaller", "🚨 Exception: ${e.localizedMessage}", e)
            NetworkResult.Error(e.localizedMessage ?: "Unexpected error")
        }
    }

    private fun <T> extractFlag(body: T): Int? = try {
        val tree = Gson().toJsonTree(body)
        if (tree is JsonObject && tree.has(Constant.FLAG)) tree.get(Constant.FLAG).asInt else null
    } catch (e: Exception) {
        Log.e("ApiCaller", "Flag parse error: ${e.message}")
        null
    }

    private fun isAuthFlag(flag: Int): Boolean =
        flag == APIResponseFlags.UN_AUTHORIZE || flag == APIResponseFlags.ACCESS_TOKEN_IS_EXPIRED

    private fun handleAuthFlag(flag: Int) {
        runBlocking { sessionStore.clearAll() }
        eventBus.emit(AppEvent.TokenExpired(reason = "Flag=$flag"))
    }
}
