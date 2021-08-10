package com.xmartlabs.gong.ui.screens.splash

import androidx.compose.runtime.Immutable

/**
 * Created by mirland on 10/8/21.
 */
sealed class SplashViewModelEvent {
    object NavigateToDashboard : SplashViewModelEvent()
    object NavigateToSignIn : SplashViewModelEvent()
}

sealed class SplashUiAction

@Immutable
data class SplashViewState(val initializing: Boolean)
