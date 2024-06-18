package com.xmartlabs.gong.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.xmartlabs.gong.R
import com.xmartlabs.gong.domain.usecase.SessionType
import com.xmartlabs.gong.ui.screens.signin.SignInScreen
import com.xmartlabs.gong.ui.screens.welcome.WelcomeScreen
import com.xmartlabs.gong.ui.theme.AppTheme
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MainActivity : ComponentActivity() {
    companion object {
        private val SPLASH_ANIMATION_TIME = 300.toDuration(DurationUnit.MILLISECONDS)
    }

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val splashWasDisplayed = savedInstanceState != null
        if (!splashWasDisplayed) {
            installSplashScreen()
                .setOnExitAnimationListener(this::handleActivityState)
        } else {
            setTheme(R.style.AppTheme)
            handleActivityState(null)
        }
    }

    private fun handleActivityState(splashScreenViewProvider: SplashScreenViewProvider?) {
        lifecycleScope.launch {
            viewModel.state
                .mapNotNull { it.sessionType }
                .collect { sessionType ->
                    if (splashScreenViewProvider == null) {
                        activityContentView(sessionType)
                    } else {
                        // Get icon instance and start a fade out animation
                        splashScreenViewProvider.iconView
                            .animate()
                            .setDuration(SPLASH_ANIMATION_TIME.inWholeMilliseconds)
                            .alpha(0f)
                            .withEndAction {
                                splashScreenViewProvider.remove()
                                activityContentView(sessionType)
                            }.start()
                    }
                }
        }
    }

    private fun activityContentView(sessionType: SessionType) {
        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                AppTheme {
                    AppNavigationManager(sessionType)
                }
            }
        }
    }
}

@Composable
fun AppNavigationManager(sessionType: SessionType) {
    val navigationController: NavHostController = rememberNavController()
    val startDestination = if (sessionType == SessionType.LOGGED) Screens.WELCOME else Screens.SIGN_IN
    NavHost(navController = navigationController, startDestination = startDestination) {
        composable(Screens.SIGN_IN) {
            SignInScreen(navController = navigationController)
        }
        composable(Screens.WELCOME) {
            WelcomeScreen(navController = navigationController)
        }
    }
}
