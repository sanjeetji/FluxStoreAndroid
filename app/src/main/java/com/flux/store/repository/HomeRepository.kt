package com.flux.store.repository

import com.flux.store.R
import com.flux.store.helper.HomeScreenEnum
import com.flux.store.model.response.Category
import com.flux.store.model.response.HomeBanner
import com.flux.store.model.response.HomePageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor() {

    private val _categoryData = MutableStateFlow<List<Category>>(emptyList())
    val categoryData: StateFlow<List<Category>> = _categoryData

    private val _homePageData = MutableStateFlow<List<HomePageData>>(emptyList())
    val homePageData: StateFlow<List<HomePageData>> = _homePageData

    init {
        _categoryData.value = listOf(
            Category(1, "Women",     R.drawable.ic_female),
            Category(2, "Men",       R.drawable.ic_male),
            Category(3, "Accessories", R.drawable.ic_accessories),
            Category(4, "Beauty",    R.drawable.ic_beauty),
            Category(5, "Shoes",     R.drawable.ic_shoes),
        )

        _homePageData.value = listOf(
            HomePageData(
                HomeScreenEnum.VIEW_PAGER_BANNER_VIEW.value,
                listOf(
                    HomeBanner(1, "Autumn Collection 2021",null,R.drawable.img_home_banner),
                    HomeBanner(2, "Autumn Collection 2022",null,R.drawable.img_home_banner),
                    HomeBanner(3, "Autumn Collection 2023",null,R.drawable.img_home_banner),
                    )
            ),
            HomePageData(
                HomeScreenEnum.SINGLE_BANNER_VIEW.value,
                listOf(
                    HomeBanner(4, "First Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(5, "Second Banner Title","This is second banner description",R.drawable.img_home_banner),
                    HomeBanner(6, "Third Banner Title","This is third banner description",R.drawable.img_home_banner)
                )
            ),
            HomePageData(
                HomeScreenEnum.LARGE_WIDE_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(7, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(8, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(9, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(10, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(11, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(12, "Single Banner Title","This is first banner description",R.drawable.img_home_banner)
                    )
            ),
            HomePageData(
                HomeScreenEnum.LARGE_TALL_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(13, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(14, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(15, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(16, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(17, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(18, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(19, "Single Banner Title","This is first banner description",R.drawable.img_home_banner)
                    )
            ),
            HomePageData(
                HomeScreenEnum.SMALL_WIDE_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(14, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(15, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(16, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(17, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(18, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(19, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(20, "Single Banner Title","This is first banner description",R.drawable.img_home_banner)
                )
            ),
            HomePageData(
                HomeScreenEnum.SMALL_TALL_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(21, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(22, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(23, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(24, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(25, "Single Banner Title","This is first banner description",R.drawable.img_home_banner),
                    HomeBanner(26, "Single Banner Title","This is first banner description",R.drawable.img_home_banner)
                )
            ),
        )

    }




}