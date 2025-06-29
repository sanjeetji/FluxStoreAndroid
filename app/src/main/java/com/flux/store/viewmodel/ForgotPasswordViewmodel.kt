package com.flux.store.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewmodel @Inject constructor():ViewModel() {


    fun verifyOtp(otp:String):Boolean{
        return otp=="123456" || otp=="1234"
    }


}