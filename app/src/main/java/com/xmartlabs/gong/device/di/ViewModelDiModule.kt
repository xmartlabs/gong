package com.xmartlabs.gong.device.di

import com.xmartlabs.gong.ui.screens.signin.SignInFragmentViewModel
import com.xmartlabs.gong.ui.screens.splash.SplashFragmentViewModel
import com.xmartlabs.gong.ui.screens.welcome.WelcomeFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object ViewModelDiModule {
  val viewModels = module {
    viewModel { SignInFragmentViewModel(get(), get()) }
    viewModel { SplashFragmentViewModel(get()) }
    viewModel { WelcomeFragmentViewModel(get(), get()) }
  }
}
