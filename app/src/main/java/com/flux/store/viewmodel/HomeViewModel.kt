package com.flux.store.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _showBottomBar = MutableStateFlow(false)
    val showBottomBar: StateFlow<Boolean> = _showBottomBar

    fun setShowBottomBar(visible: Boolean) {
        _showBottomBar.value = visible
    }

    private val _isDark = MutableStateFlow(false)
    val isDark: StateFlow<Boolean> = _isDark

    fun setDark(dark: Boolean) {
        _isDark.value = dark
    }

}