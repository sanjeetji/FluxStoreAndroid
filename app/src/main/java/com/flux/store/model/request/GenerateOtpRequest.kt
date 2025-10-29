package com.flux.store.model.request

import com.google.gson.annotations.SerializedName

data class GenerateOtpRequest(
    @SerializedName("email")
    val email: String
)
