package com.flux.store.model.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MyData(
    val name:String,
    val age:Int
): Parcelable
