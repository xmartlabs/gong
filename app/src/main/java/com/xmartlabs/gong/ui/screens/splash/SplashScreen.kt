package com.xmartlabs.gong.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.xmartlabs.gong.device.common.getDataOrNull
import com.xmartlabs.gong.domain.usecase.SessionType
import com.xmartlabs.gong.ui.Screens
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: SplashScreenViewModel = getViewModel()
    val sessionTypeResult by viewModel.currentSessionTypeStateFlow.collectAsState()
    when (sessionTypeResult.getDataOrNull()) {
        SessionType.LOGGED -> navController.navigate(Screens.WELCOME) {
            popUpTo(Screens.SPLASH) { inclusive = true }
        }
        SessionType.NOT_LOGGED -> navController.navigate(Screens.SIGN_IN) {
            popUpTo(Screens.SPLASH) { inclusive = true }
        }
    }
}
