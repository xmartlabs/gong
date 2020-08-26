package com.xmartlabs.gong.data.repository.auth

import com.xmartlabs.gong.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Created by mirland on 25/04/20.
 */
class UserLocalSource {
  private val localUsers: MutableMap<String, User> = mutableMapOf()

  suspend fun createUser(user: User): User = withContext(Dispatchers.IO) {
    @Suppress("MagicNumber")
    delay(50)
    localUsers[user.id] = user
    user
  }

  suspend fun getUser(userId: String): User {
    @Suppress("MagicNumber")
    delay(30)
    return requireNotNull(localUsers[userId])
  }
}
