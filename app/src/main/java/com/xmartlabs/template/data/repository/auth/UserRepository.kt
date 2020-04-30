package com.xmartlabs.template.data.repository.auth

/**
 * Created by mirland on 25/04/20.
 */
class UserRepository(
    private val userLocalSource: UserLocalSource,
    private val userRemoteSource: UserRemoteSource
) {

  suspend fun signIn(id: String, password: String) =
      userRemoteSource.signIn(id, password)
          .let { user -> userLocalSource.createUser(user) }

  suspend fun getUser(userId: String) = userLocalSource.getUser(userId)
}
