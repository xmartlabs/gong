package com.xmartlabs.gong.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.device.common.getDataOrNull
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.domain.usecase.SessionType
import com.xmartlabs.gong.ui.common.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by mirland on 03/05/20.
 */
class SplashScreenViewModel(
    getSessionTypeUseCase: GetSessionTypeUseCase,
) : BaseViewModel<SplashUiAction, SplashViewModelEvent, SplashViewState>(SplashViewState(true)) {
    init {
        viewModelScope.launch {
            getSessionTypeUseCase.invokeAsFlow(Unit)
                .collect { state ->
                    setState { copy(initializing = state == ProcessState.Loading) }
                    when (state.getDataOrNull()) {
                        SessionType.LOGGED -> sendOneShotEvent(SplashViewModelEvent.NavigateToDashboard)
                        SessionType.NOT_LOGGED -> sendOneShotEvent(SplashViewModelEvent.NavigateToSignIn)
                        null -> Unit
                    }
                }
        }
    }

    override suspend fun processAction(action: SplashUiAction) {}
}
