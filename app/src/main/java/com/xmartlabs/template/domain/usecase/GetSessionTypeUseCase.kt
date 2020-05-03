package com.xmartlabs.template.domain.usecase

import com.xmartlabs.template.data.repository.session.SessionRepository
import com.xmartlabs.template.domain.usecase.common.CoroutineUseCase

/**
 * Created by mirland on 03/05/20.
 */
interface GetSessionTypeUseCase : CoroutineUseCase<GetSessionTypeUseCase.Params, SessionType> {
  object Params
}

enum class SessionType {
  LOGGED,
  NOT_LOGGED
}

class GetSessionTypeUseCaseImpl(private val sessionRepository: SessionRepository) : GetSessionTypeUseCase {
  override suspend fun execute(params: GetSessionTypeUseCase.Params): SessionType =
      sessionRepository.isUserLogged()
          .let { isUserLogged -> if (isUserLogged) SessionType.LOGGED else SessionType.NOT_LOGGED }
}
