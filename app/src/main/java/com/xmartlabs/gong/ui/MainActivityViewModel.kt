package com.xmartlabs.gong.ui

import androidx.lifecycle.viewModelScope
import com.xmartlabs.gong.device.common.getDataOrNull
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by mirland on 03/05/20.
 */
class MainActivityViewModel(
    getSessionTypeUseCase: GetSessionTypeUseCase,
) : BaseViewModel<MainActivityUiAction, MainActivityEvent, SplashViewState>(SplashViewState(null)) {
    init {
        viewModelScope.launch(Dispatchers.Default) {
            getSessionTypeUseCase.invokeAsFlow(Unit)
                .collect { sessionType ->
                    setState { copy(sessionType = sessionType.getDataOrNull()) }
                }
        }
    }

    override suspend fun processAction(action: MainActivityUiAction) {}
}
