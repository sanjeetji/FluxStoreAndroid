package com.flux.store.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HomePageData(
        @SerializedName("view_type")
        val viewType: Int,
        @SerializedName("data")
        val data: List<HomeBanner>
    )

@Parcelize
data class HomeBanner(
    @SerializedName("banner_id")
    val bannerId: Int,
    @SerializedName("banner_title")
    val bannerTitle: String,
    @SerializedName("banner_description")
    val bannerDescription: String?,
    @SerializedName("banner_image")
    val bannerImage: Int
): Parcelable