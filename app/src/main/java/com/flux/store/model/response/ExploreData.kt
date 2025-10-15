package com.flux.store.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ExploreData(
    @SerializedName("drawable_image")
    val drawableImage: Int,
    @SerializedName("banner_image")
    val bannerImage: String,
    @SerializedName("banner_category")
    val bannerCategory: List<BannerCategory>
)

@Parcelize
data class BannerCategory(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("total_item")
    val totalItem: Int,
    @SerializedName("data")
    val bannerCategory: List<BannerData>
) : Parcelable

@Parcelize
data class BannerData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_fare")
    val originalFare: Double,
    @SerializedName("discounted_fare")
    val discountedFare: Double,
    @SerializedName("total_rating")
    val totalRating: Int,
    @SerializedName("average_rating")
    val averageRating: Double,
    @SerializedName("is_favourite")
    val isFavourite: Boolean
) : Parcelable
