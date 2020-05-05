package com.xmartlabs.template.device.di

import com.xmartlabs.template.ui.screens.signin.SignInFragmentViewModel
import com.xmartlabs.template.ui.screens.splash.SplashFragmentViewModel
import com.xmartlabs.template.ui.screens.welcome.WelcomeFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object ViewModelDiModule {
  val viewModels = module {
    // TODO: Review the scope
    viewModel { SignInFragmentViewModel(get(), get()) }
    viewModel { SplashFragmentViewModel(get()) }
    viewModel { WelcomeFragmentViewModel(get(), get()) }
  }
}
