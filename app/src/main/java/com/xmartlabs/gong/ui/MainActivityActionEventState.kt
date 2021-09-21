package com.xmartlabs.gong.ui

import androidx.compose.runtime.Immutable
import com.xmartlabs.gong.domain.usecase.SessionType

/**
 * Created by mirland on 10/8/21.
 */
sealed class MainActivityEvent

sealed class MainActivityUiAction

@Immutable
data class SplashViewState(val sessionType: SessionType?)
