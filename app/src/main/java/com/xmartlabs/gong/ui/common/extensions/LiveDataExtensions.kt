@file:Suppress("unused")

package com.xmartlabs.gong.ui.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.swissknife.core.extensions.exhaustive

/**
 * Created by mirland on 28/04/20.
 */
inline fun <T> LiveData<ProcessState<T>>.observeStateResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onFailure: (Throwable) -> Unit = {},
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: (T) -> Unit = {}
) = observe(viewLifecycleOwner) { processState ->
  when (processState) {
    is ProcessState.Loading -> onLoading.invoke()
    else -> invokeResultCallback((processState as ProcessState.Finish).result, onSuccess, onFailure)
  }.exhaustive
}

inline fun <T> LiveData<Result<T>>.observeResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onFailure: (Throwable) -> Unit = {},
    crossinline onSuccess: (T) -> Unit = {}
) = observe(viewLifecycleOwner) { result -> invokeResultCallback(result, onSuccess, onFailure) }

inline fun <T> invokeResultCallback(result: Result<T>, onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) = when {
  result.isSuccess -> onSuccess.invoke(result.getOrThrow())
  else -> onFailure.invoke(result.exceptionOrNull()!!)
}.exhaustive

inline fun <T> LiveData<ProcessState<T>>.observeFinishSuccessResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onSuccess: (T) -> Unit
) = observeStateResult(viewLifecycleOwner, onSuccess = onSuccess)

inline fun <T> LiveData<ProcessState<T>>.observeFinishFailureResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onFailure: (Throwable) -> Unit
) = observeStateResult(viewLifecycleOwner, onFailure = onFailure)

inline fun <T> LiveData<ProcessState<T>>.observeLoadingResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onLoading: () -> Unit
) = observeStateResult(viewLifecycleOwner, onLoading = onLoading)

inline fun <T> LiveData<Result<T>>.observeSuccessResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onSuccess: (T) -> Unit
) = observeResult(viewLifecycleOwner, onSuccess = onSuccess)

inline fun <T> LiveData<Result<T>>.observeFailureResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onFailure: (Throwable) -> Unit
) = observeResult(viewLifecycleOwner, onFailure = onFailure)
