package com.xmartlabs.template.ui.screens.signin

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.xmartlabs.template.data.model.User
import com.xmartlabs.template.domain.usecase.SignInUseCase

/**
 * Created by mirland on 25/04/20.
 */
class SignInFragmentViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {
  private val signInMutableLiveData = MutableLiveData<SignInUseCase.Params>()

  val signIn: LiveData<Result<User>> = signInMutableLiveData.switchMap { params ->
    liveData { emit(signInUseCase.invoke(params)) }
  }

  @MainThread
  fun signIn(userId: String, password: String) {
    signInMutableLiveData.value = SignInUseCase.Params(userId, password)
  }
}
