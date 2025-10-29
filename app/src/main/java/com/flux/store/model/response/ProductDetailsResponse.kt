package com.flux.store.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailsResponse(
    @SerializedName("product_id")                           val productId: Int,
    @SerializedName("product_name")                         val productName: String,
    @SerializedName("is_favourite")                         val isFavourite: Boolean,
    @SerializedName("img_list")                             val imgList: List<Int>,
    @SerializedName("original_price")                       val originalPrice: Double,
    @SerializedName("discounted_price")                     val discountedPrice: Double,
    @SerializedName("total_rating")                         val totalRating: Int,
    @SerializedName("average_rating")                       val averageRating: Double,
    @SerializedName("color_list")                           val colorList: List<String>,
    @SerializedName("size_list")                            val sizeList: List<String>,
    @SerializedName("description")                          val description: String,
    @SerializedName("rating_data")                          val reviewsData: RatingData,
    @SerializedName("review_list")                          val reviewList: List<ReviewList>,
    @SerializedName("similar_product")                      val similarProduct: List<BannerData>,
): Parcelable

@Parcelize
data class RatingData(
    @SerializedName("average_rating")                       val averageRating: Double,
    @SerializedName("total_rating")                         val totalRating: Int,
    @SerializedName("rating_data")                          val ratingData: HashMap<Int, Int>,
): Parcelable

@Parcelize
data class ReviewList(
    @SerializedName("user_id")                              val userId: Int,
    @SerializedName("user_name")                            val userName: String,
    @SerializedName("user_image")                           val userImage: String,
    @SerializedName("user_comment")                         val userComment: String,
    @SerializedName("created_at")                           val createdAt: String,
    @SerializedName("rating")                               val rating: Double,
): Parcelable

@Parcelize
data class SimilarProduct(
    @SerializedName("product_id")                           val productId: Int,
    @SerializedName("product_image")                        val productImage: String,
    @SerializedName("product_name")                         val productName: String,
    @SerializedName("original_fare")                        val originalFare: Double,
    @SerializedName("discounted_fare")                      val discountedFare: Double
): Parcelable
