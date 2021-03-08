package com.xmartlabs.gong.ui.screens.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.device.common.Result
import com.xmartlabs.gong.domain.usecase.GetLocationUseCase
import com.xmartlabs.gong.domain.usecase.LoadUserUseCase
import org.koin.java.KoinJavaComponent.inject

/**
 * Created by mirland on 25/04/20.
 */
class WelcomeScreenViewModel : ViewModel() {
  private val getLocationUseCase: GetLocationUseCase by inject(GetLocationUseCase::class.java)
  private val loadUserUseCase: LoadUserUseCase by inject(LoadUserUseCase::class.java)

  val userLiveData: LiveData<Result<User?>> = loadUserUseCase.invokeAsLiveData(Unit)

  val locationLiveData: LiveData<Result<Location>> = getLocationUseCase.invokeAsLiveData(Unit)
}
