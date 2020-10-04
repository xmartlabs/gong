package com.xmartlabs.gong.data.repository.store.datastorage

import com.google.gson.Gson
import com.xmartlabs.swissknife.datastore.DataStoreEntitySerializer

/**
 * Created by mirland on 02/10/20.
 */
class GsonDataStoreEntitySerializer(private val gson: Gson = Gson()) : DataStoreEntitySerializer {
  override fun <T> toString(t: T, aClass: Class<T>): String = gson.toJson(t)

  override fun <T> fromString(value: String?, aClass: Class<T>): T? = gson.fromJson(value, aClass)
}
