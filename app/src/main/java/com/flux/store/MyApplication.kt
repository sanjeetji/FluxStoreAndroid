package com.flux.store

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        val settings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0    // DEV only
            fetchTimeoutInSeconds        = 60
        }
        Firebase.remoteConfig.setConfigSettingsAsync(settings)
        Firebase.remoteConfig.fetchAndActivate()
            .addOnCompleteListener { /* you can log success/failure */ }
    }
}