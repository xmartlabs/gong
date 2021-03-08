package com.xmartlabs.gong.ui.screens.signin

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.device.common.Result
import com.xmartlabs.gong.domain.usecase.SignInUseCase
import com.xmartlabs.gong.domain.usecase.TimeTrackerUseCase
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import java.sql.Time
import java.util.Date

/**
 * Created by mirland on 25/04/20.
 */
class SignInScreenViewModel : ViewModel() {
  private val signInUseCase: SignInUseCase by inject(SignInUseCase::class.java)
  private val timeTrackerUseCase: TimeTrackerUseCase by inject(TimeTrackerUseCase::class.java)

  private val signInMutableLiveData = MutableLiveData<SignInUseCase.Params>()
  val viewModelTime = timeTrackerUseCase.invoke(TimeTrackerUseCase.Params(Date()))
      .asLiveData()
  val signIn: LiveData<Result<User>> = signInMutableLiveData
      .switchMap { params -> signInUseCase.invokeAsLiveData(params) }

  private val userMutableLiveData = MutableLiveData<String>()
  val userLiveData = userMutableLiveData

  var passwordMutableLiveData = MutableLiveData<String>()
  var passwordLiveData = passwordMutableLiveData

  @MainThread
  fun signIn() {
    signInMutableLiveData.value =
        SignInUseCase.Params(userLiveData.value ?: "", passwordLiveData.value ?: "")
  }

  fun updateUser(user: String) {
    userMutableLiveData.value = user
  }

  fun updatePassword(password: String) {
    passwordMutableLiveData.value = password
  }
}
