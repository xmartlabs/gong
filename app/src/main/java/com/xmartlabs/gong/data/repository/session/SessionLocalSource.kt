package com.xmartlabs.gong.data.repository.session

import com.google.gson.Gson
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.data.repository.store.SharePreferenceStore
import com.xmartlabs.gong.data.repository.store.converters.UserConverter

/**
 * Created by mirland on 03/05/20.
 */
class SessionLocalSource(private val sharePreferenceStore: SharePreferenceStore, gson: Gson) {
  companion object {
    private const val SESSION_KEY_PREFIX = "session"
    private const val TOKEN_KEY = "$SESSION_KEY_PREFIX.tokenKey"
    private const val USER_KEY = "$SESSION_KEY_PREFIX.userKey"
  }

  private val userConverter = UserConverter(gson)

  suspend fun getSessionToken() = sharePreferenceStore.getString(TOKEN_KEY)

  suspend fun getSessionUser() = sharePreferenceStore.getEntity(USER_KEY, userConverter)

  suspend fun setSession(user: User, token: String) {
    sharePreferenceStore.putString(TOKEN_KEY, token)
    sharePreferenceStore.putEntity(USER_KEY, user, userConverter)
  }
}
