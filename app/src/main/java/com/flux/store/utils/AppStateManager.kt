package com.flux.store.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFERENCES_NAME = "flag_preferences"
val Context.flagPreferencesDataStore by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class AppStateManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")

        val HAS_SEEN_INTRO = booleanPreferencesKey("has_seen_intro")
        val IS_FIRST_TIME_USER = booleanPreferencesKey("is_first_time_user")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val isDarkTheme: Flow<Boolean> = context.flagPreferencesDataStore.data
        .map { prefs -> prefs[IS_DARK_THEME] ?: false }

    val hasSeenIntro: Flow<Boolean> = context.flagPreferencesDataStore.data
        .map { prefs -> prefs[HAS_SEEN_INTRO] ?: false }

    val isFirstTimeUser: Flow<Boolean> = context.flagPreferencesDataStore.data
        .map { prefs -> prefs[IS_FIRST_TIME_USER] ?: false }

    val isLoggedIn: Flow<Boolean> = context.flagPreferencesDataStore.data
        .map { prefs -> prefs[IS_LOGGED_IN] ?: false }

    suspend fun setHasSeenIntro(seen: Boolean) {
        context.flagPreferencesDataStore.edit { it[HAS_SEEN_INTRO] = seen }
    }

    suspend fun setIsFirstTimeUser(seen: Boolean) {
        context.flagPreferencesDataStore.edit { it[HAS_SEEN_INTRO] = seen }
    }

    suspend fun setIsLoggedIn(seen: Boolean) {
        context.flagPreferencesDataStore.edit { it[HAS_SEEN_INTRO] = seen }
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        context.flagPreferencesDataStore.edit { it[IS_DARK_THEME] = enabled }
    }
}
