package com.xmartlabs.template.domain.usecase

import com.xmartlabs.template.data.model.User
import com.xmartlabs.template.data.repository.auth.UserRepository
import com.xmartlabs.template.domain.usecase.common.CoroutineUseCase

/**
 * Created by mirland on 25/04/20.
 */
interface LoadUserUseCase : CoroutineUseCase<LoadUserUseCase.Params, User> {
  data class Params(val userId: String)
}

class LoadUserUseCaseImpl(private val userRepository: UserRepository) : LoadUserUseCase {
  override suspend fun execute(params: LoadUserUseCase.Params): User =
      userRepository.getUser(params.userId)
}
