package com.xmartlabs.gong.domain.usecase.common

import com.xmartlabs.gong.device.common.ProcessState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

/**
 * Created by mirland on 25/04/20.
 */
abstract class FlowCoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(params: P): Flow<ProcessState<R>> = execute(params)
      .map<R, ProcessState<R>> { value -> ProcessState.Success(value) }
      .onStart { emit(ProcessState.Loading) }
      .catch { error ->
        Timber.w(error)
        emit(ProcessState.Failure(error))
      }
      .flowOn(coroutineDispatcher)

  /**
   * Override this to set the code to be executed.
   */
  abstract fun execute(params: P): Flow<R>
}
