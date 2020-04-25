package com.xmartlabs.template.device.common

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

  data class Success<out T>(val data: T) : Result<T>()
  data class Failure(val exception: Throwable) : Result<Nothing>()
  object Loading : Result<Nothing>()

  override fun toString(): String {
    return when (this) {
      is Success<*> -> "Success[data=$data]"
      is Failure -> "Failure[exception=$exception]"
      Loading -> "Loading"
    }
  }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.isSuccess
  get() = this is Result.Success && data != null

fun <T> Result<T>.getOrThrow() = (this as Result.Success<T>).data

fun <T> Result<T>.getOrNull() = (this as? Result.Success<T>)?.data

fun <T> Result<T>.getOr(fallback: T): T =
  (this as? Result.Success<T>)?.data ?: fallback
