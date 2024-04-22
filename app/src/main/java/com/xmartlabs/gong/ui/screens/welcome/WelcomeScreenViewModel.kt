package com.xmartlabs.gong.ui.screens.welcome

import androidx.lifecycle.viewModelScope
import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.device.common.getDataOrNull
import com.xmartlabs.gong.device.extensions.mapToProcessResult
import com.xmartlabs.gong.domain.usecase.GetLocationUseCase
import com.xmartlabs.gong.domain.usecase.LoadUserUseCase
import com.xmartlabs.gong.domain.usecase.SignOutUseCase
import com.xmartlabs.gong.ui.common.BaseViewModel
import com.xmartlabs.gong.ui.screens.signin.SignInViewModelEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by mirland on 25/04/20.
 */
class WelcomeScreenViewModel(
    getLocationUseCase: GetLocationUseCase,
    loadUserUseCase: LoadUserUseCase,
    val signOutUseCase: SignOutUseCase,
) : BaseViewModel<WelcomeUiAction, WelcomeViewModelEvent, WelcomeViewState>(WelcomeViewState()) {
    init {
        viewModelScope.launch {
            getLocationUseCase(Unit)
                .mapToProcessResult()
                .collect { updateLocation(it) }
        }
        viewModelScope.launch {
            loadUserUseCase.invokeAsFlow(Unit)
                .collect { updateUserInfo(it) }
        }
    }

    private suspend fun updateUserInfo(userProcessState: ProcessState<User?>) {
        userProcessState.getDataOrNull()?.let { user ->
            setState { copy(userName = user.name) }
        }
    }

    private suspend fun updateLocation(locationProcessState: ProcessState<Location>) {
        locationProcessState.getDataOrNull()?.let { location ->
            setState { copy(location = location) }
        }
    }

    override suspend fun processAction(action: WelcomeUiAction) {
        viewModelScope.launchWithState {
            when (action) {
                WelcomeUiAction.Logout -> signOutUseCase.invokeAsFlow(Unit).watchProcessState {
                    when (it) {
                        is ProcessState.Failure -> sendOneShotEvent(WelcomeViewModelEvent.SignOutError(it.exception))
                        is ProcessState.Success -> sendOneShotEvent(WelcomeViewModelEvent.NavigateToSignIn)
                        ProcessState.Loading -> Unit
                    }
                }
            }
        }
    }
}
