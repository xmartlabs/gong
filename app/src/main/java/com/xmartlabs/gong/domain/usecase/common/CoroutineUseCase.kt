package com.xmartlabs.gong.domain.usecase.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.xmartlabs.gong.device.common.ProcessState
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
  fun invokeAsLiveData(params: P): LiveData<ProcessState<R>> = invokeAsFlow(params).asLiveData()

  fun invokeAsFlow(params: P): Flow<ProcessState<R>> = flow {
    emit(ProcessState.Loading)
    emit(ProcessState.Finish(invoke(params)))
  }

  suspend operator fun invoke(params: P): Result<R> = try {
    // Moving all use case's executions to the injected dispatcher
    // In production code, this is usually the Default dispatcher (background thread)
    // In tests, this becomes a TestCoroutineDispatcher
    withContext(coroutineDispatcher) {
      execute(params).let {
        Result.success(it)
      }
    }
  } catch (throwable: Throwable) {
    Timber.w(throwable)
    Result.failure(throwable)
  }

  /**
   * Override this to set the code to be executed.
   */
  abstract suspend fun execute(params: P): R
}
