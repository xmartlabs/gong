package com.xmartlabs.gong.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.xmartlabs.gong.device.common.ProcessState
import androidx.lifecycle.viewModelScope
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.domain.usecase.SessionType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Created by mirland on 03/05/20.
 */
class SplashScreenViewModel(getSessionTypeUseCase: GetSessionTypeUseCase) : ViewModel() {
  val currentSessionTypeStateFlow: StateFlow<ProcessState<SessionType>> =
      getSessionTypeUseCase.invokeAsFlow(Unit)
          .stateIn(viewModelScope, SharingStarted.Lazily, ProcessState.Loading)
}
