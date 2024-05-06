package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.domain.repository.UserRepository
import com.xmartlabs.gong.domain.usecase.common.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SignOutUseCase(
    private val userRepository: UserRepository,
    dispatcher: CoroutineDispatcher,
) : CoroutineUseCase<Unit, Unit>(dispatcher) {

    override suspend fun execute(params: Unit): Unit = userRepository.signOut()
}
