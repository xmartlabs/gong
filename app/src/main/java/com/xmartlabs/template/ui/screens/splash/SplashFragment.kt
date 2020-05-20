package com.xmartlabs.template.ui.screens.splash

import com.xmartlabs.template.domain.usecase.SessionType
import com.xmartlabs.template.ui.common.BaseFragment
import com.xmartlabs.template.ui.common.extensions.observeResult
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by mirland on 03/05/20.
 */
// This fragment shouldn't have ui, the splash ui should be declared in the activity style.
class SplashFragment : BaseFragment() {
  private val viewModel: SplashFragmentViewModel by viewModel()

  override fun onResume() {
    super.onResume()
    viewModel.currentSessionTypeLiveData.observeResult(this,
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
