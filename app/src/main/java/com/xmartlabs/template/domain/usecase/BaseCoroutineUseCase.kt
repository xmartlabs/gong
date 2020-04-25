package com.xmartlabs.template.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by mirland on 25/04/20.
 */
interface BaseCoroutineUseCase<in P, R> {

  /** Executes the use case asynchronously and places the [Result] in a MutableLiveData
   *
   * @param parameters the input parameters to run the use case with
   * @param result the MutableLiveData where the result is posted to
   *
   */
  suspend operator fun invoke(parameters: P, result: MutableLiveData<Result<R>>) {
    try {
      result.postValue(Result.success(execute(parameters)))
    } catch (ex: @Suppress("TooGenericExceptionCaught") Throwable) {
      result.postValue(Result.failure(ex))
    }
  }

  /** Executes the use case asynchronously and returns a [Result] in a new LiveData object.
   *
   * @return an observable [LiveData] with a [Result].
   *
   * @param params the input parameters to run the use case with
   */
  suspend operator fun invoke(params: P): Result<R> =
    try {
      Result.success(execute(params))
    } catch (ex: @Suppress("TooGenericExceptionCaught") Throwable) {
      Result.failure(ex)
    }

  /**
   * Override this to set the code to be executed.
   */
  suspend fun execute(params: P): R
}

suspend operator fun <R> BaseCoroutineUseCase<Unit, R>.invoke(): Result<R> = this(Unit)
// suspend operator fun <R> BaseCoroutineUseCase<Unit, R>.invoke(result: MutableLiveData<Result<R>>) = this(Unit, result)
