package com.flux.store.helper

enum class HomeScreenEnum(val value: Int) {

    VIEW_PAGER_BANNER_VIEW(1),
    SINGLE_BANNER_VIEW(2),
    HORIZONTAL_WIDE_BANNER_LIST_VIEW(3),
    VERTICAL_WIDE_BANNER_LIST_VIEW(4),
    HORIZONTAL_TALL_BANNER_LIST_VIEW(5),
    VERTICAL_TALL_BANNER_LIST_VIEW(6),
    UNDEFINED_VIEW(7);
    fun asInt(): Int = value
}