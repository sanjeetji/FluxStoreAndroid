package com.flux.store.model.response

import com.google.gson.annotations.SerializedName

data class GenerateOtpResponse(
    @SerializedName("flag") val flag: Int,
    @SerializedName("message") val message: String,
    @SerializedName("driver_id") val driverId: Int
)
