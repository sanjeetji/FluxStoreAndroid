package com.flux.store.fakeData.screenState

import com.flux.store.model.response.BaseResponse
import com.flux.store.viewmodel.UiState
import com.google.gson.annotations.SerializedName

data class ProductScreenState(
    val productDetails: UiState<BaseResponse<FakeProductDetailsResponse>> = UiState.Loading,
    val similarProducts: UiState<BaseResponse<List<FakeProduct>>> = UiState.Loading,
    val reviews: UiState<BaseResponse<FakeReviewResponse>> = UiState.Loading
)

data class FakeProductDetailsResponse(
    @SerializedName("id")                 val id:Int,
    @SerializedName("name")               val name:String,
    @SerializedName("description")        val description:String,
    @SerializedName("price")              val price:String
)

data class FakeProduct(
    @SerializedName("id")                 val id:Int,
    @SerializedName("name")               val name:String,
    @SerializedName("description")        val description:String,
    @SerializedName("price")              val price:String
)

data class FakeReviewResponse(
    @SerializedName("id")                 val id:Int,
    @SerializedName("name")               val name:String,
    @SerializedName("description")        val description:String,
    @SerializedName("price")              val price:String
)