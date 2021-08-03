package com.xmartlabs.gong.domain.usecase.common

import com.xmartlabs.gong.device.common.ProcessResult
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.device.common.onFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 * https://github.com/google/iosched/blob/ee7f31c16f2d1e1f20f479b384dccb205e3e9584/shared/src/main/java/com/google/samples/apps/iosched/shared/domain/CoroutineUseCase.kt
 */
abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default) {
    fun invokeAsFlow(params: P): Flow<ProcessState<R>> = flow { emit(invoke(params)) }
        .mapResultToProcessState()

    suspend operator fun invoke(params: P): ProcessResult<R> =
        withContext(coroutineDispatcher) {
            ProcessResult.runCatching { execute(params) }
                .onFailure { Timber.w(it) }
        }

    /**
     * Override this to set the code to be executed.
     */
    abstract suspend fun execute(params: P): R
}
