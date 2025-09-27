package com.flux.store.repository

import com.flux.store.R
import com.flux.store.helper.HomeScreenEnum
import com.flux.store.model.response.Category
import com.flux.store.model.response.DataHeader
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
                    HomeBanner(1, "Autumn Collection 2021",null,"https://images.unsplash.com/photo-1583316174775-bd6dc0e9f298?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", DataHeader("New Collection","View All")),
                    HomeBanner(2, "Autumn Collection 2022",null,"https://images.unsplash.com/photo-1685875018148-6ac6d41b7c4e?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", DataHeader("New Collection","View All")),
                    HomeBanner(3, "Autumn Collection 2023",null,"https://images.unsplash.com/photo-1560859259-fcf2b952aed8?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", DataHeader("New Collection","View All")),
                    )
            ),
            HomePageData(
                HomeScreenEnum.SINGLE_BANNER_VIEW.value,
                listOf(
                    HomeBanner(4, "Free Shipping for Prepaid Order In India.","Red Color Trendy Georgette Floral Print Women's Gown","", DataHeader("New Collection","View All")),
                    HomeBanner(4, "Free Shipping for Prepaid Order In India.","Red Color Trendy Georgette Floral Print Women's Gown","https://clothsvilla.com/cdn/shop/products/RedColorTrendyGeorgetteFloralPrintWomen_sGown_1_1024x1024.jpg?v=1676299608", DataHeader("New Collection","View All")),
                    HomeBanner(5, "Traditional Pakistani Eid Dresses","Pakistani Eid Clothing Collection 2025 for Women at Filhaal UK","https://filhaal.co.uk/cdn/shop/articles/Pakistani_Eid_Clothing_Collection_002e6c3a-8959-45ba-beff-e6b4061a51bc.png?v=1742959117&width=3000", DataHeader("New Collection","View All")),
                )
            ),
            HomePageData(
                HomeScreenEnum.LARGE_WIDE_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(7, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(8, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(9, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(10, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(11, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(12, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All"))
                    )
            ),
            HomePageData(
                HomeScreenEnum.LARGE_TALL_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(7, "Best Dresses to Wear on Family Functions","No matter how many times you pull that little black dress out of your closet, it always looks stunning","https://st3.depositphotos.com/1177973/13134/i/1600/depositphotos_131343702-stock-photo-different-female-clothes.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(8, "Finding the perfect dress can be tricky, especially if you want something stylish.","11 of the best Amazon dresses for women over 40, according to an expert — all under $100","https://images.stockcake.com/public/f/4/9/f4943121-b1c4-401c-a7d9-a7c35545988f_large/colorful-wardrobe-collection-stockcake.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(15, "Festive Wear Guide- What to Wear to A Festival This Year","It might sound too good to be true, but festivals are officially back this year 2021. And by this, we mean that things are getting better out there, and we can finally start celebrating.","https://media.istockphoto.com/id/935032524/photo/women-summer-dresses-on-display-at-camden-market.jpg?s=1024x1024&w=is&k=20&c=tJOs8y-0XOCDy5Mtzsj4FRiPv8EGe1cUPbXp4UuSEuY=", DataHeader("New Collection","View All")),
                    HomeBanner(16, "Top States for Selling Women’s Dresses in India","Let’s explore the top states in India where women’s dresses are sold the most, along with some insights on the types of fabrics famous from these regions.","https://thevishnu.in/cdn/shop/articles/blog1_vishnu_1600x.png?v=1718008804", DataHeader("New Collection","View All")),
                    )
            ),
            HomePageData(
                HomeScreenEnum.LARGE_TALL_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(7, "5 Clothing Store Layout Must-Haves.","Maximize engagement and boost sales using these creative design elements","https://www.resonai.com/hs-fs/hubfs/Google%20Drive%20Integration/5%20Clothing%20Store%20Layout%20Must%20Haves%20for%202022.png?width=1248&height=832&name=5%20Clothing%20Store%20Layout%20Must%20Haves%20for%202022.png", DataHeader("New Collection","View All")),
                    HomeBanner(8, "Store Planning: The Ultimate Guide for Retail.","From racetrack layouts to color psychology — here’s everything you need to know to create a store plan that will meet the most demanding KPIs","https://www.resonai.com/hs-fs/hubfs/Google%20Drive%20Integration/Store%20Planning%20The%20Ultimate%20Guide%20for%20Retail.jpeg?width=1248&height=832&name=Store%20Planning%20The%20Ultimate%20Guide%20for%20Retail.jpeg", DataHeader("New Collection","View All")),
                    HomeBanner(15, "The best fashion shops in Bangkok.","From quaint boutiques to high-end designer stores, fashion therapy is never far from reach in Bangkok.","https://media.timeout.com/images/103260890/1920/1440/image.webp", DataHeader("New Collection","View All")),
                    HomeBanner(16, "6 Reasons Why Pre-Loved Fashion Is The Hottest Craze.","We are in the year of pre-loved fashion, and I know you’re going to absolutely love this movement as much as we do at Current Boutique!.","https://cdn.shopify.com/s/files/1/0269/9644/1191/files/Easy-Hacks-to-Make-Your-Clothing-Last-Longer-2048x1365.jpg", DataHeader("New Collection","View All")),
                )
            ),
            HomePageData(
                HomeScreenEnum.SMALL_WIDE_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(14, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(15, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(16, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(17, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(18, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(19, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(20, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All"))
                )
            ),
            HomePageData(
                HomeScreenEnum.SMALL_TALL_BANNER_LIST_VIEW.value,
                listOf(
                    HomeBanner(21, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(22, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(23, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(24, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(25, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All")),
                    HomeBanner(26, "Single Banner Title","This is first banner description","https://petitekingdom.com/wp-content/uploads/2022/01/kleitas-meitenem-petite-kingdom.jpg", DataHeader("New Collection","View All"))
                )
            ),
        )

    }




}