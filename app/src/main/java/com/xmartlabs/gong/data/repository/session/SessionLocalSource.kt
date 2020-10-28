package com.xmartlabs.gong.data.repository.session

import com.xmartlabs.gong.data.model.User
import com.xmartlabs.swissknife.datastore.DataStoreSource
import kotlinx.coroutines.flow.first

/**
 * Created by mirland on 03/05/20.
 */
class SessionLocalSource(private val dataStoreSource: DataStoreSource) {
  companion object {
    private const val SESSION_KEY_PREFIX = "session"
    private const val TOKEN_KEY = "$SESSION_KEY_PREFIX.tokenKey"
    private const val USER_KEY = "$SESSION_KEY_PREFIX.userKey"
  }

  suspend fun getSessionToken() = dataStoreSource.getEntity<String>(TOKEN_KEY)
      .first()

  fun getSessionUser() = dataStoreSource.getEntity<User>(USER_KEY)

  suspend fun setSession(user: User, token: String) {
    dataStoreSource.putEntity(TOKEN_KEY, token)
    dataStoreSource.putEntity(USER_KEY, user)
  }
}
