package com.flux.store.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HomePageData(
        @SerializedName("view_type")
        val viewType: Int,
        @SerializedName("data")
        val data: List<HomeBanner>,
        @SerializedName("data_header")
        val dataHeader: DataHeader,
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
    val bannerImage: String,
): Parcelable

@Parcelize
data class DataHeader(
    @SerializedName("header_title")
    val headerTitle: String,
    @SerializedName("view_all")
    val viewAll: String
): Parcelable