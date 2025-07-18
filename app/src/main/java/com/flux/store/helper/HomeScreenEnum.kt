package com.flux.store.helper

enum class HomeScreenEnum(val value: Int) {

    VIEW_PAGER_BANNER_VIEW(1),
    SINGLE_BANNER_VIEW(2),
    LARGE_WIDE_BANNER_LIST_VIEW(3),
    LARGE_TALL_BANNER_LIST_VIEW(4),
    SMALL_WIDE_BANNER_LIST_VIEW(5),
    SMALL_TALL_BANNER_LIST_VIEW(6),
    UNDEFINED_VIEW(7);
    fun asInt(): Int = value

}