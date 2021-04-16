package com.xmartlabs.gong.device.common

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ProcessState<out R> {
  data class Finish<out T>(val result: Result<T>) : ProcessState<T>()
  object Loading : ProcessState<Nothing>()

  override fun toString(): String = when (this) {
    is Loading -> "Loading"
    is Finish<*> -> "Finish = Result[$result]"
  }
}
