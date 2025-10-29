package com.flux.store.fakeData.fakeNetwork

import com.flux.store.R
import com.flux.store.model.response.BannerData
import com.flux.store.model.response.ProductDetailsResponse
import com.flux.store.model.response.RatingData
import com.flux.store.model.response.ReviewList


val productDetailsData = ProductDetailsResponse(
    1,
    "Sportwear Set",
    true,
    listOf(
        R.drawable.img_prod_1,
        R.drawable.img_prod_2,
        R.drawable.img_prod_3,
    ),
//            listOf(
//                "https://wforwoman.com/cdn/shop/files/SP11494-401086.jpg?v=1758861989&width=1500",
//                "https://wforwoman.com/cdn/shop/files/SP11506-401100_2.jpg?v=1758777844&width=1500",
//                "https://wforwoman.com/cdn/shop/files/24AUSP11990-124426_1_f6b1c6aa-0cba-44a0-b043-30ced7f9c986.jpg?v=1739786248&width=1500"
//                ),
    435.9,
    400.50,
    45,
    averageRating = 3.5,
    listOf("#faafaf", "#e2f0b1", "#219ced", "#e83aa5", "#f7394c"),
    listOf("S", "M", "L", "XL", "XXL"),
    "Sportswear is no longer under culture, it is no longer indie or cobbled together as it once was. Sport is fashion today. The top is oversized in fit and style, may need to size down.",
    RatingData(
        3.5, 34, mapOf(
            Pair(1, 20),
            Pair(2, 30),
            Pair(3, 30),
            Pair(4, 30),
            Pair(5, 30),
            Pair(6, 30),
            Pair(7, 30),
            Pair(8, 50),
            Pair(9, 80),
            Pair(10, 80),
            Pair(11, 80),
            Pair(12, 80),
            Pair(13, 80),
            Pair(14, 80),
            Pair(15, 100),
        ) as HashMap<Int, Int>
    ),
    listOf(
        ReviewList(
            1,
            "Kamal",
            "",
            "I love it.  Awesome customer service!! Helped me out with adding an additional item to my order. Thanks again!",
            "",
            4.5
        ),
        ReviewList(
            1,
            "Kamal",
            "",
            "I love it.  Awesome customer service!! Helped me out with adding an additional item to my order. Thanks again!",
            "",
            4.5
        ),
        ReviewList(
            1,
            "Kamal",
            "",
            "I love it.  Awesome customer service!! Helped me out with adding an additional item to my order. Thanks again!",
            "",
            4.5
        ),
        ReviewList(
            1,
            "Kamal",
            "",
            "I love it.  Awesome customer service!! Helped me out with adding an additional item to my order. Thanks again!",
            "",
            4.5
        ),
        ReviewList(
            1,
            "Kamal",
            "",
            "I love it.  Awesome customer service!! Helped me out with adding an additional item to my order. Thanks again!",
            "",
            4.5
        ),
    ),
    listOf(
        BannerData(1, R.drawable.img_girl_5, "Rise Crop Hoodie", 345.6, 200.5, 450, 65.0, false),
        BannerData(1, R.drawable.img_girl_5, "Rise Crop Hoodie", 345.6, 200.5, 450, 65.0, false),
        BannerData(1, R.drawable.img_girl_5, "Rise Crop Hoodie", 345.6, 200.5, 450, 65.0, false),
        BannerData(1, R.drawable.img_girl_5, "Rise Crop Hoodie", 345.6, 200.5, 450, 65.0, false),
        BannerData(1, R.drawable.img_girl_5, "Rise Crop Hoodie", 345.6, 200.5, 450, 65.0, false),
    )
)