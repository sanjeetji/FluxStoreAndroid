package com.flux.store.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginRegistrationViewmodel @Inject constructor():ViewModel() {

    fun registerUser(name: String, email: String, phone: String, password: String) {

    }

    fun loginUser(email: String,password: String) {

    }

}