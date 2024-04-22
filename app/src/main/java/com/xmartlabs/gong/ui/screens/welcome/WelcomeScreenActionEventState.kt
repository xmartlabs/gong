package com.xmartlabs.gong.ui.screens.welcome

import androidx.compose.runtime.Immutable
import com.xmartlabs.gong.data.model.Location
import com.xmartlabs.gong.ui.screens.signin.SignInViewModelEvent

/**
 * Created by mirland on 22/4/21.
 */
sealed class WelcomeViewModelEvent {
    data object NavigateToSignIn: WelcomeViewModelEvent()
    class SignOutError(val throwable: Throwable) : WelcomeViewModelEvent()


}

sealed class WelcomeUiAction {
    data object Logout : WelcomeUiAction();
}

@Immutable
data class WelcomeViewState(
    val userName: String = "",
    val location: Location? = null,
)
