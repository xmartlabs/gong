package com.xmartlabs.template.ui.screens.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.xmartlabs.template.databinding.FragmentWelcomeBinding
import com.xmartlabs.template.ui.common.BaseFragment
import com.xmartlabs.template.ui.common.extensions.observeSuccessResult
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by mirland on 25/04/20.
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {
  private val viewModel: WelcomeFragmentViewModel by viewModel()

  override fun inflateViewBinding(): FragmentWelcomeBinding = FragmentWelcomeBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViewModel()
  }

  @SuppressLint("SetTextI18n")
  private fun setupViewModel() = with(viewModel) {
    userLiveData.observeSuccessResult(viewLifecycleOwner) { user ->
      viewBinding.titleTextView.text = "Hi ${user!!.name}"
    }
    locationLiveData.observeSuccessResult(viewLifecycleOwner) { location ->
      val locationName = listOfNotNull(location.city, location.country)
          .joinToString(", ")
      if (locationName.isNotBlank()) {
        viewBinding.locationTextView.text = "You signed in from $locationName!"
      }
    }
  }
}
