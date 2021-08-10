package com.xmartlabs.gong.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.xmartlabs.gong.R
import com.xmartlabs.gong.device.common.getDataOrNull
import com.xmartlabs.gong.domain.usecase.SessionType
import com.xmartlabs.gong.ui.Screens
import com.xmartlabs.gong.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: SplashScreenViewModel = getViewModel()
    val sessionTypeResult by viewModel.currentSessionTypeStateFlow.collectAsState()

    LaunchedEffect(navController, sessionTypeResult.getDataOrNull()) {
        when (sessionTypeResult.getDataOrNull()) {
            SessionType.LOGGED ->
                navController.navigate(Screens.WELCOME) {
                    popUpTo(Screens.SPLASH) { inclusive = true }
                }
            SessionType.NOT_LOGGED ->
                navController.navigate(Screens.SIGN_IN) {
                    popUpTo(Screens.SPLASH) { inclusive = true }
                }
        }
    }
    SplashContent()
}

@Composable
private fun SplashContent() {
    Surface(
        color = Color.Yellow,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(AppTheme.colors.primary)
        ) {
            Image(
                painter = painterResource(R.drawable.xl_logo),
                contentDescription = "App Logo",
            )
        }
    }
}
