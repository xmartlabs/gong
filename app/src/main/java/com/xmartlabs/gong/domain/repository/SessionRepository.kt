package com.xmartlabs.gong.domain.repository

import com.xmartlabs.gong.data.repository.session.SessionLocalSource

/**
 * Created by mirland on 03/05/20.
 */
class SessionRepository(private val sessionLocalSource: SessionLocalSource) {
    private suspend fun getSessionToken() = sessionLocalSource.getSessionToken()
    suspend fun isUserLogged() = !getSessionToken().isNullOrBlank()
}
