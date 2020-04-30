package com.xmartlabs.template.domain.usecase

import com.xmartlabs.template.data.model.User
import com.xmartlabs.template.data.repository.auth.UserRepository
import com.xmartlabs.template.domain.usecase.common.CoroutineUseCase

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
