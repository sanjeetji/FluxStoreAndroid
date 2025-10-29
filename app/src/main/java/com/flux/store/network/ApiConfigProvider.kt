package com.flux.store.network

import com.flux.store.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ✅ Centralized configuration for environment URLs.
 *
 * Only build type matters:
 * - Debug → Dev server
 * - Release → Prod server
 */
@Singleton
class ApiConfigProvider @Inject constructor() {

    val activeBaseUrl: String
        get() = BuildConfig.BASE_URL  // set directly per build type

    val googleApiBaseUrl: String get() = BuildConfig.GOOGLE_API_BASE_URL
    val olaApiBaseUrl: String get() = BuildConfig.OLA_API_BASE_URL
    val chatApiBaseUrl: String get() = BuildConfig.CHAT_BASE_URL
}
