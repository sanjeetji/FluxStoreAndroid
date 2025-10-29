package com.flux.store.model.response
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("flag") val flag: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T? = null
)
