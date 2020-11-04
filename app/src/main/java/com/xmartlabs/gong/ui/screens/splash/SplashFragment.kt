package com.xmartlabs.gong.ui.screens.splash

import com.xmartlabs.gong.domain.usecase.SessionType
import com.xmartlabs.gong.ui.common.BaseFragment
import com.xmartlabs.gong.ui.common.extensions.observeStateResult
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by mirland on 03/05/20.
 */
// This fragment shouldn't have ui, the splash ui should be declared in the activity style.
class SplashFragment : BaseFragment() {
  private val viewModel: SplashFragmentViewModel by viewModel()

  override fun onResume() {
    super.onResume()
    viewModel.currentSessionTypeLiveData.observeStateResult(this,
        onFailure = { ex -> throw IllegalStateException("Invalid state", ex) },
        onSuccess = { sessionType ->
          val direction = when (sessionType) {
            SessionType.LOGGED -> SplashFragmentDirections.actionSplashFragmentToWelcomeFragment()
            SessionType.NOT_LOGGED ->
              SplashFragmentDirections.actionSplashFragmentToSignInFragment()
          }
          router.navigate(direction)
        })
  }
}
