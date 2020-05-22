package com.xmartlabs.template.domain.repository

import com.xmartlabs.template.data.repository.auth.UserLocalSource
import com.xmartlabs.template.data.repository.auth.UserRemoteSource
import com.xmartlabs.template.data.repository.session.SessionLocalSource

/**
 * Created by mirland on 25/04/20.
 */
class UserRepository(
    private val userLocalSource: UserLocalSource,
    private val userRemoteSource: UserRemoteSource,
    private val sessionLocalSource: SessionLocalSource
) {

  suspend fun signIn(id: String, password: String) =
      userRemoteSource.signIn(id, password)
          .let { response ->
            userLocalSource.createUser(response.user)
            sessionLocalSource.setSession(response.user, response.token)
            response.user
          }

  suspend fun getCurrentUser() = sessionLocalSource.getSessionUser()

  suspend fun getUser(userId: String) = userLocalSource.getUser(userId)
}
