package com.xmartlabs.gong.device.di

import com.xmartlabs.gong.ui.screens.signin.SignInScreenViewModel
import com.xmartlabs.gong.ui.MainActivityViewModel
import com.xmartlabs.gong.ui.screens.welcome.WelcomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by mirland on 25/04/20.
 */
object ViewModelDiModule {
    val viewModels = module {
        viewModel { SignInScreenViewModel(get()) }
        viewModel { MainActivityViewModel(get()) }
        viewModel { WelcomeScreenViewModel(get(), get(), get()) }
    }
}
