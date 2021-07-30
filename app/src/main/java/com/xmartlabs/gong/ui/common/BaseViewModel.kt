package com.xmartlabs.gong.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xmartlabs.gong.device.common.ProcessResult
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.device.common.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Created by mirland on 21/4/21.
 */
abstract class BaseViewModel<Action, OneShotEvent, State>(initialState: State) : ViewModel() {
  private val _state = MutableStateFlow(initialState)
  private val stateMutex = Mutex()

  val state: StateFlow<State>
    get() = _state.asStateFlow()

  // See https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055
  // For why channel > SharedFlow/StateFlow in this case
  private val oneShotEventsChannel = Channel<OneShotEvent>(Channel.BUFFERED)
  val oneShotEvents = oneShotEventsChannel.receiveAsFlow()

  private val pendingActions = MutableSharedFlow<Action>()

  init {
    viewModelScope.launch {
      pendingActions.collect { action ->
        processAction(action)
      }
    }
  }

  protected abstract suspend fun processAction(action: Action)

  protected suspend fun sendOneShotEvent(event: OneShotEvent) = oneShotEventsChannel.send(event)

  fun submitAction(action: Action) {
    viewModelScope.launch {
      pendingActions.emit(action)
    }
  }

  protected suspend fun setState(reducer: State.() -> State) {
    stateMutex.withLock {
      _state.value = reducer(_state.value)
    }
  }

  protected fun CoroutineScope.launchSetState(reducer: State.() -> State) {
    launch { setState(reducer) }
  }

  protected fun CoroutineScope.launchWithState(block: (State) -> Unit) {
    launch {
      withState(block)
    }
  }

  protected suspend fun withState(block: (State) -> Unit) {
    stateMutex.withLock {
      block(_state.value)
    }
  }

  protected fun CoroutineScope.withState(block: (State) -> Unit) {
    launch { this@BaseViewModel.withState(block) }
  }

  protected inline fun <T> Flow<ProcessState<T>>.watchProcessState(
    crossinline action: suspend (ProcessState<T>) -> Unit,
  ) = viewModelScope.launch {
    collect(action)
  }

  protected inline fun <T> Flow<ProcessState<T>>.watchProcessResult(crossinline action: (ProcessResult<T>) -> Unit) =
    viewModelScope.launch {
      mapNotNull { it.asResult() }
        .collect { action(it) }
    }
}
