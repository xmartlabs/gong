package com.xmartlabs.gong.ui.screens.signin

import androidx.compose.runtime.Immutable

/**
 * Created by mirland on 22/4/21.
 */
sealed class SignInViewModelEvent {
    object NavigateToDashboard : SignInViewModelEvent()
    class SignInError(val throwable: Throwable) : SignInViewModelEvent()
}

sealed class SignInUiAction {
    object SignIn : SignInUiAction()
    data class ChangePassword(val password: String) : SignInUiAction()
    data class ChangeUserName(val userName: String) : SignInUiAction()
}

@Immutable
data class SignInViewState(
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)
