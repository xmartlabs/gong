package com.xmartlabs.gong.device.logger

import androidx.navigation.NavController
import com.xmartlabs.swissknife.core.Logger
import com.xmartlabs.swissknife.navigationdebug.extensions.setupOnDestinationChangedLogger
import timber.log.Timber
import java.util.Collections
import java.util.WeakHashMap

/**
 * Created by mirland on 25/05/20.
 */
class DebugNavigationLogger : NavigationLogger {
  companion object {
    private const val PATH_CHANGE_TAG = "Nav-path"
  }

  private var navControllers: MutableSet<NavController> = Collections.newSetFromMap(WeakHashMap())

  private val logger: Logger = object : Logger {
    override fun log(message: String) = Timber.tag(PATH_CHANGE_TAG)
        .d(message)
  }

  override fun setup(navController: NavController) {
    if (navController !in navControllers) {
      navControllers.add(navController)
      navController.setupOnDestinationChangedLogger(logger)
    }
  }
}
