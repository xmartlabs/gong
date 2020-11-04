package com.xmartlabs.gong.ui.screens.signin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.xmartlabs.gong.databinding.FragmentSigninBinding
import com.xmartlabs.gong.ui.common.BaseViewBindingFragment
import com.xmartlabs.gong.ui.common.extensions.observeStateResult
import com.xmartlabs.gong.ui.common.extensions.observeSuccessResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.ExperimentalTime

/**
 * Created by mirland on 25/04/20.
 */
class SignInFragment : BaseViewBindingFragment<FragmentSigninBinding>() {
  private val viewModel: SignInFragmentViewModel by viewModel()

  override fun inflateViewBinding(): FragmentSigninBinding =
      FragmentSigninBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupButtons()
    setupViewModelCallbacks()
  }

  @SuppressLint("SetTextI18n")
  @OptIn(ExperimentalTime::class)
  private fun setupViewModelCallbacks() = with(viewModel) {
    viewModelTime.observeSuccessResult(viewLifecycleOwner) { duration ->
      viewBinding.viewModelTimeTextView.text = "View model time, ${duration.inSeconds} seconds"
    }
    signIn.observeStateResult(viewLifecycleOwner,
        onFailure = { throwable ->
          if (throwable is SecurityException) {
            Toast.makeText(
                requireContext(),
                "password or username is wrong, try with userUd = 'xmartlabs', password 'xmartlabs'",
                Toast.LENGTH_SHORT
            ).show()
          }
        },
        onSuccess = {
          router.navigate(
              SignInFragmentDirections.actionSignInFragmentToWelcomeFragment()
          )
        }
    )
  }

  private fun setupButtons() = withViewBinding {
    signInButton.setOnClickListener {
      viewModel.signIn(
          userIdEditText.text.toString(),
          passwordEditText.text.toString()
      )
    }
  }
}
