package com.xmartlabs.template.data.repository.auth

import com.xmartlabs.template.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Created by mirland on 25/04/20.
 */
class UserRemoteSource {
  companion object {
    private const val VALID_PASSWORD = "xmartlabs"
    private const val VALID_ID = "xmartlabs"
    private val XMARTLABS_USER = User("xmartlabs", "xmartlabs", "hi@xmartlabs.com")
  }

  suspend fun signIn(id: String, password: String) = withContext(Dispatchers.IO) {
    @Suppress("MagicNumber")
    delay(100) // Simulate network delay
    if (id.toLowerCase(Locale.ROOT) == VALID_ID && password == VALID_PASSWORD) {
      XMARTLABS_USER
    } else {
      throw SecurityException("Invalid User")
    }
  }
}
