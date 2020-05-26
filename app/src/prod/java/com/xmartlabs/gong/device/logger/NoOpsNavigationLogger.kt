package com.xmartlabs.gong.device.logger

import androidx.navigation.NavController

/**
 * Created by mirland on 25/05/20.
 */
class NoOpsNavigationLogger : NavigationLogger {
  override fun setup(navController: NavController) {}
}
