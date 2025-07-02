package com.flux.store.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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


    var count by mutableIntStateOf(0)
        private set

    fun incrementCount() {
        count++
    }


    var ex = mutableStateOf(0)

    fun saveValue(value:Int){
        ex.value = value
    }
    fun getValue():Int{
        return ex.value
    }
}