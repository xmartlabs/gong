package com.xmartlabs.template.ui.screens.signin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.xmartlabs.template.R
import com.xmartlabs.template.databinding.FragmentSigninBinding
import com.xmartlabs.template.ui.common.BaseFragment
import com.xmartlabs.template.ui.common.extensions.navigateSafe
import com.xmartlabs.template.ui.common.extensions.observeResult
import com.xmartlabs.template.ui.common.extensions.observeSuccessResult
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.time.ExperimentalTime

/**
 * Created by mirland on 25/04/20.
 */
class SignInFragment : BaseFragment<FragmentSigninBinding>() {
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
    signIn.observeResult(viewLifecycleOwner,
        onFailure = { throwable ->
          if (throwable is SecurityException) {
            Toast.makeText(
                requireContext(),
                "password or username is wrong, try with userUd = 'xmartlabs', password 'xmartlabs'",
                Toast.LENGTH_SHORT
            ).show()
          }
        },
        onSuccess = { result ->
          requireActivity().findNavController(R.id.fragment_container).navigateSafe(
              SignInFragmentDirections.actionSignInFragmentToWelcomeFragment(result.id)
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
