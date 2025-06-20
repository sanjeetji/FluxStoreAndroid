package com.flux.store.utils

object Helper {


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.length in 7..13 && phone.all { it.isDigit() }
    }
}