package com.xmartlabs.gong.device.extensions

import com.dropbox.android.external.store4.ExperimentalStoreApi
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreResponse
import com.dropbox.android.external.store4.doThrow
import com.xmartlabs.gong.device.common.ProcessResult
import com.xmartlabs.gong.device.common.ProcessState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber

private val storeHelperCoroutineScope = CoroutineScope(Dispatchers.Default) +
    CoroutineExceptionHandler { _, exception ->
      Timber.w(exception, "Unhandled error happened")
    } + SupervisorJob()

@OptIn(ExperimentalStoreApi::class)
fun <Key : Any, Output : Any> Store<Key, Output>.clearWhen(flow: Flow<Unit>) = also {
  storeHelperCoroutineScope.launch {
    flow.collect {
      clearAll()
    }
  }
}

val StoreResponse.Error.exception: Throwable
  get() = (ProcessResult.runCatching { doThrow() } as ProcessState.Failure).exception

inline fun <T, E> StoreResponse<T>.map(mapper: (T) -> E): StoreResponse<E> = when (this) {
  is StoreResponse.Data<T> -> StoreResponse.Data(mapper.invoke(value), origin)
  is StoreResponse.Loading -> this
  is StoreResponse.NoNewData -> this
  is StoreResponse.Error -> this
}

fun StoreResponse<*>.errorOrNull() = (this as? StoreResponse.Error)?.exception

fun <T> Flow<StoreResponse<T>>.filterSuccess(
    emitErrorsMutableFlow: MutableSharedFlow<StoreResponse.Error>? = null,
) = mapNotNull { state ->
  if (state is StoreResponse.Error) {
    emitErrorsMutableFlow?.emit(state)
  }
  (state as? StoreResponse.Data)?.dataOrNull()
}

inline fun <T> Flow<StoreResponse<T>>.filterSuccessTrackingErrors(crossinline messageCallback: (Throwable) -> String) =
    trackError(messageCallback)
        .filterSuccess()

inline fun <T> Flow<StoreResponse<T>>.trackError(crossinline messageCallback: (Throwable) -> String) =
    onEach { response ->
      val error = response.errorOrNull()
      if (error != null) {
        Timber.w(error, messageCallback.invoke(error))
      }
    }

fun <T> Flow<StoreResponse<T>>.mapToProcessResult(): Flow<ProcessState<T>> =
    mapNotNull { response: StoreResponse<T> ->
      when (response) {
        is StoreResponse.Data<T> -> ProcessState.Success(response.requireData())
        is StoreResponse.Loading -> ProcessState.Loading
        is StoreResponse.NoNewData -> null
        is StoreResponse.Error -> ProcessState.Failure(response.exception)
      }
    }

fun <T> MutableStateFlow<T?>.toSimpleEntitySourceOfTruth() = object : SourceOfTruth<Unit, T, T> {
  override suspend fun delete(key: Unit) {
    value = null
  }

  override suspend fun deleteAll() {
    value = null
  }

  override fun reader(key: Unit): Flow<T?> = this@toSimpleEntitySourceOfTruth

  override suspend fun write(key: Unit, value: T) {
    this@toSimpleEntitySourceOfTruth.value = value
  }
}
