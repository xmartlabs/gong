package com.xmartlabs.template.ui.screens.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.xmartlabs.template.data.model.User
import com.xmartlabs.template.domain.usecase.LoadUserUseCase

/**
 * Created by mirland on 25/04/20.
 */
class WelcomeFragmentViewModel(private val loadUserUseCase: LoadUserUseCase) : ViewModel() {
  private val loadUserMutableLiveData = MutableLiveData<LoadUserUseCase.Params>()
  val userLiveData: LiveData<Result<User>> = loadUserMutableLiveData.switchMap { params ->
    liveData { emit(loadUserUseCase.invoke(params)) }
  }

  fun loadUser(userId: String) {
    loadUserMutableLiveData.value = LoadUserUseCase.Params(userId)
  }
}
