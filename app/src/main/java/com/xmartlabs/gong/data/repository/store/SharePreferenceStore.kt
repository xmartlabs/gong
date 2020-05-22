package com.xmartlabs.gong.data.repository.store

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import com.xmartlabs.gong.data.repository.store.converters.SharePreferenceEntityConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by mirland on 03/05/20.
 */
interface SharePreferenceStore {
  @WorkerThread
  fun getStringOnCurrentThread(key: String, defaultValue: String? = null): String? // It's used to get the access token

  suspend fun getString(key: String, defaultValue: String? = null): String?
  fun putString(key: String, value: String?)
  suspend fun <T> getEntity(key: String, converter: SharePreferenceEntityConverter<T>): T?
  suspend fun <T> putEntity(key: String, t: T, converter: SharePreferenceEntityConverter<T>)
}

class SharePreferenceStoreImpl(
    private val sharedPreferences: SharedPreferences
) : SharePreferenceStore {
  private val cachedEntity: MutableMap<String, Any?> = mutableMapOf()
  override suspend fun <T> putEntity(key: String, t: T, converter: SharePreferenceEntityConverter<T>) =
      withContext(Dispatchers.Default) {
        t.saveInCache(key)
        sharedPreferences.edit()
            .putString(key, converter.toString(t))
            .apply()
      }

  override fun putString(key: String, value: String?) =
      sharedPreferences.edit()
          .putString(key, value)
          .apply()
          .saveInCache(key)

  @Suppress("UNCHECKED_CAST")
  override suspend fun <T> getEntity(key: String, converter: SharePreferenceEntityConverter<T>): T? =
      (cachedEntity[key] as? T)
          ?: withContext(Dispatchers.Default) {
            val json = sharedPreferences.getString(key, null)
            converter.fromString(json)
          }.saveInCache(key)

  override fun getStringOnCurrentThread(key: String, defaultValue: String?): String? = (cachedEntity[key] as? String)
      ?: sharedPreferences.getString(key, defaultValue)
          .saveInCache(key)

  override suspend fun getString(key: String, defaultValue: String?): String? = (cachedEntity[key] as? String)
      ?: withContext(Dispatchers.Default) {
        sharedPreferences.getString(key, defaultValue)
            .saveInCache(key)
      }

  private fun <T> T.saveInCache(key: String) = also { cachedEntity[key] = this }
}
