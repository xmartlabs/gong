package com.xmartlabs.template.ui.screens.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xmartlabs.template.data.model.Location
import com.xmartlabs.template.data.model.User
import com.xmartlabs.template.device.common.Result
import com.xmartlabs.template.domain.usecase.GetLocationUseCase
import com.xmartlabs.template.domain.usecase.LoadUserUseCase

/**
 * Created by mirland on 25/04/20.
 */
class WelcomeFragmentViewModel(
    getLocationUseCase: GetLocationUseCase,
    private val loadUserUseCase: LoadUserUseCase
) : ViewModel() {
  private val loadUserMutableLiveData = MutableLiveData<LoadUserUseCase.Params>()
  val userLiveData: LiveData<Result<User>> = loadUserMutableLiveData
    .switchMap { params -> loadUserUseCase.invoke(params) }

  val locationLiveData: LiveData<Result<Location>> = getLocationUseCase.invoke(GetLocationUseCase.Params())

  fun loadUser(userId: String) {
    loadUserMutableLiveData.value = LoadUserUseCase.Params(userId)
  }
}
