package com.xmartlabs.template.domain.usecase.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.xmartlabs.template.device.common.Result

/**
 * Created by mirland on 25/04/20.
 */
interface CoroutineUseCase<in P, R> {
  operator fun invoke(params: P): LiveData<Result<R>> = liveData {
    emit(Result.Loading)
    emit(
      try {
        Result.Success(execute(params))
      } catch (ex: @Suppress("TooGenericExceptionCaught") Throwable) {
        Result.Failure(ex)
      }
    )
  }

  /**
   * Override this to set the code to be executed.
   */
  suspend fun execute(params: P): R
}
