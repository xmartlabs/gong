package com.xmartlabs.gong.ui.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.domain.usecase.SessionType

/**
 * Created by mirland on 03/05/20.
 */
class SplashScreenViewModel(getSessionTypeUseCase: GetSessionTypeUseCase) : ViewModel() {
  val currentSessionTypeLiveData: LiveData<ProcessState<SessionType>> =
      getSessionTypeUseCase.invokeAsLiveData(Unit)
}
