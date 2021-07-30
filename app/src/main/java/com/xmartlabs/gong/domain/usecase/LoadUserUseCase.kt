package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.domain.repository.UserRepository
import com.xmartlabs.gong.domain.usecase.common.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first

/**
 * Created by mirland on 25/04/20.
 */
class LoadUserUseCase(
    private val userRepository: UserRepository,
    dispatcher: CoroutineDispatcher,
) : CoroutineUseCase<Unit, User?>(dispatcher) {
    override suspend fun execute(params: Unit): User? =
        userRepository.getCurrentUser()
            .first()
}
