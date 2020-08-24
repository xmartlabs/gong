package com.xmartlabs.gong.domain.usecase.common

import androidx.lifecycle.asLiveData
import com.xmartlabs.gong.device.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
 * Created by mirland on 25/04/20.
 */
abstract class FlowCoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(params: P): Flow<Result<R>> = execute(params)
      .map<R, Result<R>> { value -> Result.Success<R>(value) }
      .catch { error ->
        Timber.w(error)
        emit(Result.Failure(error))
      }
      .flowOn(coroutineDispatcher)

  fun invokeAsLiveData(params: P) = invoke(params)
      .asLiveData()

  /**
   * Override this to set the code to be executed.
   */
  abstract fun execute(params: P): Flow<R>
}
