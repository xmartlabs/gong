package com.xmartlabs.gong.ui.common

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.xmartlabs.gong.device.logger.NavigationLogger
import com.xmartlabs.swissknife.navigation.extensions.navigateSafe
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by mirland on 14/05/20.
 */
interface NavRouter {
  fun getCurrentDestination(): NavDestination?
  fun setGraph(@NavigationRes graphResId: Int)
  fun onUpButtonTapped(): Boolean
  fun popBackStack(): Boolean
  fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean): Boolean
  fun navigate(directions: NavDirections, navOptions: NavOptions? = null)
  fun navigate(
      @IdRes resId: Int,
      args: Bundle? = null,
      navOptions: NavOptions? = null,
      navExtras: Navigator.Extras? = null
  )
}

open class NavRouterImpl : NavRouter, KoinComponent {
  private lateinit var navController: NavController
  private val navigationLogger: NavigationLogger by inject()

  fun setupNavController(navController: NavController) {
    this.navController = navController
    navigationLogger.setup(navController)
  }

  override fun getCurrentDestination(): NavDestination? = navController.currentDestination

  override fun setGraph(@NavigationRes graphResId: Int) = navController.setGraph(graphResId)

  override fun onUpButtonTapped() = navController.navigateUp()

  override fun popBackStack() = navController.popBackStack()

  override fun popBackStack(destinationId: Int, inclusive: Boolean) =
      navController.popBackStack(destinationId, inclusive)

  override fun navigate(directions: NavDirections, navOptions: NavOptions?) =
      navController.navigateSafe(directions, navOptions)

  override fun navigate(resId: Int, args: Bundle?, navOptions: NavOptions?, navExtras: Navigator.Extras?) =
      navController.navigateSafe(resId, args, navOptions, navExtras)
}

class ActivityNavRouter : NavRouterImpl() {
  fun setupNavController(activity: Activity, @IdRes navControllerId: Int) =
      setupNavController(activity.findNavController(navControllerId))
}

class FragmentNavRouter(fragment: Fragment) : NavRouterImpl() {
  init {
    setupNavController(fragment.findNavController())
  }
}
