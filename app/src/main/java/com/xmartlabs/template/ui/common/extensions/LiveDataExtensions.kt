@file:Suppress("unused")

package com.xmartlabs.template.ui.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.xmartlabs.template.device.common.Result

/**
 * Created by mirland on 28/04/20.
 */
inline fun <T> LiveData<Result<T>>.observeResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onFailure: (Throwable) -> Unit = {},
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: (T) -> Unit = {}
) = observe(viewLifecycleOwner, Observer { result ->
  when (result) {
    is Result.Success -> onSuccess.invoke(result.data)
    is Result.Failure -> onFailure.invoke(result.exception)
    is Result.Loading -> onLoading.invoke()
  }
})

inline fun <T> LiveData<Result<T>>.observeSuccessResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onSuccess: (T) -> Unit
) = observeResult(viewLifecycleOwner, onSuccess = onSuccess)

inline fun <T> LiveData<Result<T>>.observeFailureResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onFailure: (Throwable) -> Unit
) = observeResult(viewLifecycleOwner, onFailure = onFailure)

inline fun <T> LiveData<Result<T>>.observeLoadingResult(
    viewLifecycleOwner: LifecycleOwner,
    crossinline onLoading: () -> Unit
) = observeResult(viewLifecycleOwner, onLoading = onLoading)
