package com.xmartlabs.template.ui.screens.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.xmartlabs.template.R
import com.xmartlabs.template.databinding.FragmentSigninBinding
import com.xmartlabs.template.ui.common.BaseFragment
import com.xmartlabs.template.ui.common.extensions.navigateSafe
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

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

  private fun setupViewModelCallbacks() {
    viewModel.signIn.observe(viewLifecycleOwner, Observer { result ->
      when {
        // TODO: Refactor the navigation
        result.isSuccess -> requireActivity().findNavController(R.id.fragment_container).navigateSafe(
          SignInFragmentDirections.actionSignInFragmentToWelcomeFragment(result.getOrThrow().id)
        )
        result.exceptionOrNull() is SecurityException ->
          Toast.makeText(
            requireContext(),
            "password or username is wrong, try with userUd = 'xmartlabs', password 'xmartlabs'",
            Toast.LENGTH_SHORT
          ).show()
      }
    })
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
