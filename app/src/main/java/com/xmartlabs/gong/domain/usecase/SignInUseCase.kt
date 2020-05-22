package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.domain.repository.UserRepository
import com.xmartlabs.gong.domain.usecase.common.CoroutineUseCase

/**
 * Created by mirland on 25/04/20.
 */
interface SignInUseCase : CoroutineUseCase<SignInUseCase.Params, User> {
  data class Params(val id: String, val password: String)
}

class SignInUseCaseImpl(private val userRepository: UserRepository) : SignInUseCase {
  override suspend fun execute(params: SignInUseCase.Params): User =
      userRepository.signIn(params.id, params.password)
}
