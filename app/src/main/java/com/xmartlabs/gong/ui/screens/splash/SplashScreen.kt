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
import com.xmartlabs.gong.ui.Screens
import com.xmartlabs.gong.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: SplashScreenViewModel = getViewModel()
    val event: SplashViewModelEvent? by viewModel.oneShotEvents.collectAsState(initial = null)
    SplashEvents(navController, event)
    SplashContent()
}

@Composable
private fun SplashEvents(
    navController: NavHostController,
    event: SplashViewModelEvent?,
) {
    LaunchedEffect(navController, event) {
        when (event) {
            SplashViewModelEvent.NavigateToDashboard -> navController.navigate(Screens.WELCOME) {
                popUpTo(Screens.SPLASH) { inclusive = true }
            }
            SplashViewModelEvent.NavigateToSignIn -> navController.navigate(Screens.SIGN_IN) {
                popUpTo(Screens.SPLASH) { inclusive = true }
            }
            null -> Unit
        }
    }
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
