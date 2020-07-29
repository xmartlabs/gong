package com.xmartlabs.gong.data.repository.session

import com.google.gson.Gson
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.data.repository.store.sharedPreferences.SharedPreferencesStore
import com.xmartlabs.gong.data.repository.store.sharedPreferences.converters.UserConverter

/**
 * Created by mirland on 03/05/20.
 */
class SessionLocalSource(private val sharedPreferencesStore: SharedPreferencesStore, gson: Gson) {
  companion object {
    private const val SESSION_KEY_PREFIX = "session"
    private const val TOKEN_KEY = "$SESSION_KEY_PREFIX.tokenKey"
    private const val USER_KEY = "$SESSION_KEY_PREFIX.userKey"
  }

  private val userConverter = UserConverter(gson)

  suspend fun getSessionToken() = sharedPreferencesStore.getString(TOKEN_KEY)

  suspend fun getSessionUser() = sharedPreferencesStore.getEntity(USER_KEY, userConverter)

  suspend fun setSession(user: User, token: String) {
    sharedPreferencesStore.putString(TOKEN_KEY, token)
    sharedPreferencesStore.putEntity(USER_KEY, user, userConverter)
  }
}
