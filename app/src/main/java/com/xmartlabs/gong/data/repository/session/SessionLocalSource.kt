package com.xmartlabs.gong.data.repository.session

import androidx.datastore.core.DataStore
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.data.model.service.settings.AppSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Created by mirland on 03/05/20.
 */
class SessionLocalSource(
    private val sessionDataStore: DataStore<AppSettings>,
) {
  private val updateLock = Mutex()

  suspend fun getSessionToken() = sessionDataStore.data
      .map { it.sessionToken }
      .first()

  fun getSessionUser() = sessionDataStore.data.map { it.sessionUser }

  suspend fun setSession(user: User, token: String) = updateLock.withLock {
    sessionDataStore.updateData {
      it.copy(
          sessionToken = token,
          sessionUser = user,
      )
    }
  }
}
