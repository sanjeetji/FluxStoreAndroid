package com.flux.store.utils

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationManager @Inject constructor(
  internal val state: AppStateManager
) {
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
  private val gson = Gson()
  private val mapType = object : TypeToken<Map<String, String>>() {}.type

  private val _strings = MutableStateFlow<Map<String,String>>(emptyMap())
  val strings: StateFlow<Map<String,String>> = _strings

  init {
    state.selectedLanguage
      .distinctUntilChanged()
      .onEach { lang -> loadLanguage(lang) }
      .launchIn(scope)
  }

  suspend fun setLanguage(lang: String) {
    state.setSelectedLanguage(lang)
  }

  fun loadLanguage(lang: String) {
    val key = "${lang}_languages"
    scope.launch {
      try {
        // 1) fetch + activate using the settings you applied in MyApplication
        Firebase.remoteConfig.fetchAndActivate().await()
        Log.d("LocMgr", "✔ fetchAndActivate for '$key'")

        // 2) grab the raw JSON
        val json = Firebase.remoteConfig.getString(key)
        Log.d("LocMgr", "Raw JSON for '$key': $json")

        // 3) parse and publish
        val parsed = try {
          gson.fromJson<Map<String,String>>(json, mapType)
            ?: throw IllegalStateException("Gson returned null")
        } catch (e: Exception) {
          Log.e("LocMgr","JSON parse failed for '$key'",e)
          emptyMap()
        }
        _strings.value = parsed
      } catch (e: Exception) {
        Log.e("LocMgr","RemoteConfig error for '$key'",e)
      }
    }
  }
}
