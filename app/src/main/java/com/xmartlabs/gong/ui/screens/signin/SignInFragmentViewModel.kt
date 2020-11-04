package com.xmartlabs.gong.ui.screens.signin

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.xmartlabs.gong.data.model.User
import com.xmartlabs.gong.device.common.ProcessState
import com.xmartlabs.gong.domain.usecase.SignInUseCase
import com.xmartlabs.gong.domain.usecase.TimeTrackerUseCase
import java.util.Date
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Created by mirland on 25/04/20.
 */
class SignInFragmentViewModel(
    private val signInUseCase: SignInUseCase,
    timeTrackerUseCase: TimeTrackerUseCase
) : ViewModel() {
  private val signInMutableLiveData = MutableLiveData<SignInUseCase.Params>()
  @OptIn(ExperimentalTime::class)
  val viewModelTime: LiveData<Result<Duration>> = timeTrackerUseCase.invoke(TimeTrackerUseCase.Params(Date()))
      .asLiveData()
  val signIn: LiveData<ProcessState<User>> = signInMutableLiveData
      .switchMap { params -> signInUseCase.invokeAsLiveData(params) }

  @MainThread
  fun signIn(userId: String, password: String) {
    signInMutableLiveData.value = SignInUseCase.Params(userId, password)
  }
}
