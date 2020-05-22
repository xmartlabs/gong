package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.domain.repository.UserRepository
import com.xmartlabs.gong.domain.usecase.common.CoroutineUseCase

/**
 * Created by mirland on 25/04/20.
 */
interface LoadUserUseCase : CoroutineUseCase<LoadUserUseCase.Params, User?> {
  class Params
}

class LoadUserUseCaseImpl(private val userRepository: UserRepository) : LoadUserUseCase {
  override suspend fun execute(params: LoadUserUseCase.Params): User? =
      userRepository.getCurrentUser()
}
