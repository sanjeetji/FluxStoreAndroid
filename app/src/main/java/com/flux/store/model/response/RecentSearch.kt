package com.flux.store.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecentSearch(
    @SerializedName("id")
    val recentSearchId: Int,
    @SerializedName("name")
    val recentSearchName: String
): Parcelable
