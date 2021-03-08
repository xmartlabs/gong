package com.xmartlabs.gong.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.xmartlabs.gong.domain.usecase.SessionType
import com.xmartlabs.gong.ui.Screens
import com.xmartlabs.gong.ui.common.extensions.observeResult

@Composable
fun SplashScreen(navController: NavHostController) {
  val viewModel: SplashFragmentViewModel = viewModel()
  val lifecycleOwner = LocalLifecycleOwner.current
  viewModel.currentSessionTypeLiveData.observeResult(lifecycleOwner,
      onFailure = { ex -> throw IllegalStateException("Invalid state", ex) },
      onSuccess = { sessionType ->
        when (sessionType) {
          SessionType.LOGGED -> navController.navigate(Screens.WELCOME) {
            popUpTo(Screens.SPLASH) { inclusive = true }
          }
          SessionType.NOT_LOGGED -> navController.navigate(Screens.SIGN_IN) {
            popUpTo(Screens.SPLASH) { inclusive = true }
          }
        }
      }
  )
}
