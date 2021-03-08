package com.xmartlabs.gong.ui.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xmartlabs.gong.device.common.Result
import com.xmartlabs.gong.domain.usecase.GetSessionTypeUseCase
import com.xmartlabs.gong.domain.usecase.SessionType
import org.koin.java.KoinJavaComponent.inject

/**
 * Created by mirland on 03/05/20.
 */
class SplashFragmentViewModel : ViewModel() {
  private val getSessionTypeUseCase: GetSessionTypeUseCase by inject(GetSessionTypeUseCase::class.java)

  val currentSessionTypeLiveData: LiveData<Result<SessionType>> =
      getSessionTypeUseCase.invokeAsLiveData(Unit)
}
