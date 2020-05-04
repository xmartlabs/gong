package com.xmartlabs.template.ui.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xmartlabs.template.device.common.Result
import com.xmartlabs.template.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.template.domain.usecase.SessionType

/**
 * Created by mirland on 03/05/20.
 */
class SplashFragmentViewModel(getSessionTypeUseCase: GetSessionTypeUseCase) : ViewModel() {
  val currentSessionTypeLiveData: LiveData<Result<SessionType>> =
      getSessionTypeUseCase.invoke(GetSessionTypeUseCase.Params)
}
