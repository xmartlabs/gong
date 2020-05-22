package com.xmartlabs.gong.ui.screens.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.device.common.Result
import com.xmartlabs.gong.domain.usecase.GetLocationUseCase
import com.xmartlabs.gong.domain.usecase.LoadUserUseCase

/**
 * Created by mirland on 25/04/20.
 */
class WelcomeFragmentViewModel(
    getLocationUseCase: GetLocationUseCase,
    loadUserUseCase: LoadUserUseCase
) : ViewModel() {

  val userLiveData: LiveData<Result<User?>> = loadUserUseCase.invoke(LoadUserUseCase.Params())

  val locationLiveData: LiveData<Result<Location>> = getLocationUseCase.invoke(GetLocationUseCase.Params())
}
