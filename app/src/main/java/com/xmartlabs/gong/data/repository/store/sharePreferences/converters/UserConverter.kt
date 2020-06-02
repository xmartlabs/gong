package com.xmartlabs.gong.data.repository.store.sharePreferences.converters

import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.device.extensions.fromJson

/**
 * Created by mirland on 03/05/20.
 */
class UserConverter(private val gson: Gson) : SharePreferenceEntityConverter<User> {
  @WorkerThread
  override fun toString(t: User): String = gson.toJson(t)

  @WorkerThread
  override fun fromString(value: String?): User? = gson.fromJson(value)
}
