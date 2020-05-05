package com.xmartlabs.template.ui.screens.splash

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.xmartlabs.template.R
import com.xmartlabs.template.domain.usecase.SessionType
import com.xmartlabs.template.ui.common.extensions.navigateSafe
import com.xmartlabs.template.ui.common.extensions.observeResult
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by mirland on 03/05/20.
 */
// This fragment shouldn't have ui, the splash ui should be declared in the activity style.
class SplashFragment : Fragment() {
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
          requireActivity().findNavController(R.id.fragment_container).navigateSafe(direction)
        })
  }
}
