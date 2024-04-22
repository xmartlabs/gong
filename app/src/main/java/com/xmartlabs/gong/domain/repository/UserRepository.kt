package com.xmartlabs.gong.domain.repository

import com.xmartlabs.gong.data.repository.auth.UserLocalSource
import com.xmartlabs.gong.data.repository.auth.UserRemoteSource
import com.xmartlabs.gong.data.repository.session.SessionLocalSource
import com.xmartlabs.swissknife.core.extensions.orDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext

/**
 * Created by mirland on 25/04/20.
 */
class UserRepository(
    private val userLocalSource: UserLocalSource,
    private val userRemoteSource: UserRemoteSource,
    private val sessionLocalSource: SessionLocalSource,
) {

    suspend fun signIn(id: String, password: String) =
        userRemoteSource.signIn(id, password)
            .let { response ->
                userLocalSource.createUser(response.user)
                sessionLocalSource.setSession(response.user, response.token)
                response.user
            }

    suspend fun signOut(): Unit =
        sessionLocalSource.getSessionUser().first()
            ?.let { response ->
                userLocalSource.deleteUser(response)
            } ?: Unit


    fun getCurrentUser() = sessionLocalSource.getSessionUser()

    suspend fun getUser(userId: String) = userLocalSource.getUser(userId)
}
