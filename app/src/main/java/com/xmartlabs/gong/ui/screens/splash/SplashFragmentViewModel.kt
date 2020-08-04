package com.xmartlabs.gong.ui.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xmartlabs.gong.device.common.Result
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.domain.usecase.SessionType

/**
 * Created by mirland on 03/05/20.
 */
class SplashFragmentViewModel(getSessionTypeUseCase: GetSessionTypeUseCase) : ViewModel() {
  val currentSessionTypeLiveData: LiveData<Result<SessionType>> =
      getSessionTypeUseCase.invokeAsLiveData(Unit)
}
