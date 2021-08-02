package com.xmartlabs.gong.device.common

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ProcessState<out R> {
    object Loading : ProcessState<Nothing>()
    data class Success<out T>(val data: T) : ProcessState<T>(), ProcessResult<T>
    data class Failure(val exception: Throwable) : ProcessState<Nothing>(), ProcessResult<Nothing>

    override fun toString(): String = when (this) {
        is Loading -> "Loading"
        is Success<*> -> "Success[data=$data]"
        is Failure -> "Failure[exception=$exception]"
    }
}

sealed interface ProcessResult<out T> {
    companion object {
        inline fun <T> runCatching(block: () -> T): ProcessResult<T> = try {
            ProcessState.Success(block())
        } catch (ex: Throwable) {
            ProcessState.Failure(ex)
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> ProcessState<T>.asResult(): ProcessResult<T>? =
    this as? ProcessResult<T>

fun <T> ProcessResult<T>.getDataOrNull() = (this as? ProcessState.Success<T>)?.data

fun <T> ProcessResult<T>.asProcessState(): ProcessState<T> = when (this) {
    is ProcessState.Failure -> this
    is ProcessState.Success -> this
}

fun <T> ProcessState<T>.getDataOrNull() = (this as? ProcessState.Success<T>)?.data

inline fun <T> ProcessResult<T>.onFailure(action: (exception: Throwable) -> Unit): ProcessResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is ProcessState.Failure) action(exception)
    return this
}

inline fun <T> ProcessResult<T>.onSuccess(action: (value: T) -> Unit): ProcessResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is ProcessState.Success<T>) action(data)
    return this
}

fun ProcessState<*>.isLoading(): Boolean {
    contract {
        returns(true) implies (this@isLoading is ProcessState.Loading)
    }
    return this is ProcessState.Loading
}

fun <T> ProcessState<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is ProcessState.Success<T>)
    }
    return this is ProcessState.Success<T>
}

fun ProcessState<*>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is ProcessState.Failure)
    }
    return this is ProcessState.Failure
}
