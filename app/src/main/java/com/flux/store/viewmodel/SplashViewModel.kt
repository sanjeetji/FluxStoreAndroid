package com.flux.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.flux.store.helper.AppStateManager
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appStateManager: AppStateManager
):ViewModel() {

    private val _hasSeenIntro = MutableStateFlow(false)
    val hasSeenIntro: StateFlow<Boolean> = _hasSeenIntro

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    init {
        viewModelScope.launch {
            _hasSeenIntro.value = true//make it true to skip intro screen
            _isUserLoggedIn.value = true//appStateManager.isLoggedIn.first()
        }
    }
}