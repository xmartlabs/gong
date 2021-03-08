package com.xmartlabs.gong.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xmartlabs.gong.R
import com.xmartlabs.gong.ui.screens.signin.SignInScreen
import com.xmartlabs.gong.ui.screens.splash.SplashScreen
import com.xmartlabs.gong.ui.screens.welcome.WelcomeScreen
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme_NoActionBar)
    super.onCreate(savedInstanceState)
    setContent {
      GongNavigationManager()
    }
  }

  @Composable
  fun GongNavigationManager() {
    val navigationController: NavHostController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Screens.SPLASH) {
      composable(Screens.SPLASH) { SplashScreen(navController = navigationController) }
      composable(Screens.SIGN_IN) { SignInScreen(navController = navigationController) }
      composable(Screens.WELCOME) { WelcomeScreen() }
    }
  }
}
