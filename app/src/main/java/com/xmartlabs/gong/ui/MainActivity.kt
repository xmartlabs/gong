package com.xmartlabs.gong.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.xmartlabs.gong.R
import com.xmartlabs.gong.ui.screens.signin.SignInScreen
import com.xmartlabs.gong.ui.screens.splash.SplashScreen
import com.xmartlabs.gong.ui.screens.welcome.WelcomeScreen
import com.xmartlabs.gong.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                AppTheme {
                    AppNavigationManager()
                }
            }
        }
    }

    @Composable
    fun AppNavigationManager() {
        val navigationController: NavHostController = rememberNavController()
        NavHost(navController = navigationController, startDestination = Screens.SPLASH) {
            composable(Screens.SPLASH) { SplashScreen(navController = navigationController) }
            composable(Screens.SIGN_IN) { SignInScreen(navController = navigationController) }
            composable(Screens.WELCOME) { WelcomeScreen() }
        }
    }
}
