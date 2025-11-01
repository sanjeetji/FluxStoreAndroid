package com.flux.store.viewmodel

import androidx.lifecycle.ViewModel
import com.flux.store.model.response.Category
import com.flux.store.model.response.ExploreData
import com.flux.store.model.response.HomePageData
import com.flux.store.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    // ---------- PUBLIC flows that the UI reads ----------
    private val _categoryData = MutableStateFlow<List<Category>>(emptyList())
    val categoryData: StateFlow<List<Category>> = _categoryData

    private val _homeBannerData = MutableStateFlow<List<HomePageData>>(emptyList())
    val homeBannerData: StateFlow<List<HomePageData>> = _homeBannerData

    private val _exploreData = MutableStateFlow<List<ExploreData>>(emptyList())
    val explorePageData: StateFlow<List<ExploreData>> = _exploreData

    private val _showBottomBar = MutableStateFlow(false)
    val showBottomBar: StateFlow<Boolean> = _showBottomBar

    // ----------------------------------------------------
    // Real data (network) – only used at runtime
    // ----------------------------------------------------
    init {
        // In real app you would collect from repository here
        // For preview we override the flows manually (see preview)
        _categoryData.value = homeRepository.categoryData.value
        _homeBannerData.value = homeRepository.homePageData.value
        _exploreData.value = homeRepository.exploreData.value
    }

    fun setShowBottomBar(visible: Boolean) {
        _showBottomBar.value = visible
    }

    // ---- Helper for preview only (not used in production) ----
    internal fun previewInjectData(
        categories: List<Category>,
        pageData: List<HomePageData>,
        exploreData: List<ExploreData>,
        bottomBar: Boolean = true
    ) {
        _categoryData.value = categories
        _homeBannerData.value = pageData
        _exploreData.value = exploreData
        _showBottomBar.value = bottomBar
    }

}