package com.xmartlabs.gong.ui.screens.welcome

import androidx.compose.runtime.Immutable
import com.xmartlabs.gong.data.model.Location

/**
 * Created by mirland on 22/4/21.
 */
sealed class WelcomeViewModelEvent

sealed class WelcomeUiAction

@Immutable
data class WelcomeViewState(
    val userName: String = "",
    val location: Location? = null,
)
