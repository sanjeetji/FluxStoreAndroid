package com.flux.store.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val DEVICE_TOKEN = stringPreferencesKey("device_token")
        val PHONE_NUMBER = stringPreferencesKey("phone_number")
    }

    suspend fun setAccessToken(value: String?) {
        dataStore.edit { prefs ->
            if (value == null) prefs.remove(Keys.ACCESS_TOKEN) else prefs[Keys.ACCESS_TOKEN] = value
        }
    }

    val accessToken: Flow<String?> = dataStore.data.map { it[Keys.ACCESS_TOKEN] }

    suspend fun setDeviceToken(value: String?) {
        dataStore.edit { prefs ->
            if (value == null) prefs.remove(Keys.DEVICE_TOKEN) else prefs[Keys.DEVICE_TOKEN] = value
        }
    }

    val deviceToken: Flow<String?> = dataStore.data.map { it[Keys.DEVICE_TOKEN] }

    suspend fun clearAll() { dataStore.edit { it.clear() } }
}
