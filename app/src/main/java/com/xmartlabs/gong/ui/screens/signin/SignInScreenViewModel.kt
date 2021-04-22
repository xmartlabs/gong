package com.xmartlabs.gong.ui.screens.signin

import androidx.lifecycle.viewModelScope
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.domain.usecase.SignInUseCase
import com.xmartlabs.gong.ui.common.BaseViewModel

/**
 * Created by mirland on 25/04/20.
 */
class SignInScreenViewModel(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<
    SignInUiAction,
    SignInViewModelEvent,
    SignInViewState
    >(SignInViewState()) {

  override suspend fun processAction(action: SignInUiAction) = when (action) {
    SignInUiAction.SignIn -> signIn()
    is SignInUiAction.ChangePassword -> setState { copy(password = action.password) }
    is SignInUiAction.ChangeUserName -> setState { copy(userName = action.userName) }
  }

  private suspend fun signIn() {
    viewModelScope.launchWithState { state ->
      signInUseCase.invokeAsFlow(SignInUseCase.Params(state.userName, state.password))
          .watchProcessState { state ->
            setState { copy(isLoading = state == ProcessState.Loading) }
            when (state) {
              is ProcessState.Failure -> sendOneShotEvent(SignInViewModelEvent.SignInError(state.exception))
              is ProcessState.Success -> sendOneShotEvent(SignInViewModelEvent.NavigateToDashboard)
            }
          }
    }
  }
}
