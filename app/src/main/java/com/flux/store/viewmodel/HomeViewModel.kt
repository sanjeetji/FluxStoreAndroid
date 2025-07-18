package com.flux.store.viewmodel

import androidx.lifecycle.ViewModel
import com.flux.store.model.response.Category
import com.flux.store.model.response.HomePageData
import com.flux.store.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val homeRepository: HomeRepository): ViewModel() {

    private val _showBottomBar = MutableStateFlow(false)
    val showBottomBar: StateFlow<Boolean> = _showBottomBar

    val categoryData : StateFlow<List<Category>> = homeRepository.categoryData
    val homeBannerData : StateFlow<List<HomePageData>> = homeRepository.homePageData

    fun setShowBottomBar(visible: Boolean) {
        _showBottomBar.value = visible
    }

    private val _isDark = MutableStateFlow(false)
    val isDark: StateFlow<Boolean> = _isDark

    fun setDark(dark: Boolean) {
        _isDark.value = dark
    }


}