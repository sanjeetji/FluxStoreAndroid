package com.flux.store.network

import com.flux.store.model.request.GenerateOtpRequest
import com.flux.store.model.response.BaseResponse
import com.flux.store.model.response.GenerateOtpResponse
import com.flux.store.model.response.ProductDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // OTP endpoint
    @POST("/api/user/login")
    suspend fun generateOtp(
        @Body params: GenerateOtpRequest
    ): Response<BaseResponse<GenerateOtpResponse>>

    // Product details endpoint
    @GET("/api/products/{productId}")
    suspend fun getProductDetails(
        @Path("productId") productId: Int
    ): Response<BaseResponse<ProductDetailsResponse>>
}
