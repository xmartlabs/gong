package com.xmartlabs.template.data.repository.store.converters

import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.xmartlabs.template.data.model.User
import com.xmartlabs.template.device.extensions.fromJson

/**
 * Created by mirland on 03/05/20.
 */
class UserConverter(private val gson: Gson) : SharePreferenceEntityConverter<User> {
  @WorkerThread
  override fun toString(t: User): String = gson.toJson(t)

  @WorkerThread
  override fun fromString(value: String?): User? = gson.fromJson(value)
}
