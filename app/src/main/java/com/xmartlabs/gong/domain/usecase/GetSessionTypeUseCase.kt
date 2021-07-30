package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.domain.repository.SessionRepository
import com.xmartlabs.gong.domain.usecase.common.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by mirland on 03/05/20.
 */
class GetSessionTypeUseCase(
    private val sessionRepository: SessionRepository,
    dispatcher: CoroutineDispatcher,
) : CoroutineUseCase<Unit, SessionType>(dispatcher) {
    override suspend fun execute(params: Unit): SessionType =
        if (sessionRepository.isUserLogged()) SessionType.LOGGED else SessionType.NOT_LOGGED
}

enum class SessionType {
    LOGGED,
    NOT_LOGGED
}
