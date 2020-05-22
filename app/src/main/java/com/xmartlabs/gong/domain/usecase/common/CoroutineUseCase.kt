package com.xmartlabs.gong.domain.usecase.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.xmartlabs.gong.device.common.Result
import timber.log.Timber

/**
 * Created by mirland on 25/04/20.
 */
interface CoroutineUseCase<in P, R> {
  operator fun invoke(params: P): LiveData<Result<R>> = liveData {
    emit(Result.Loading)
    emit(
        try {
          Result.Success(execute(params))
        } catch (throwable: Throwable) {
          Timber.w(throwable)
          Result.Failure(throwable)
        }
    )
  }

  /**
   * Override this to set the code to be executed.
   */
  suspend fun execute(params: P): R
}
