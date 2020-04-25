package com.xmartlabs.template.ui.common.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

/**
 * Created by mirland on 25/04/20.
 */
fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
  val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
  if (action != null && currentDestination?.id != action.destinationId) {
    navigate(resId, args, navOptions, navExtras)
  }
}

fun NavController.navigateSafe(
    direction: NavDirections,
    navOptions: NavOptions? = null
) {
  val action = currentDestination?.getAction(direction.actionId) ?: graph.getAction(direction.actionId)
  if (action != null && currentDestination?.id != action.destinationId) {
    navigate(direction, navOptions)
  }
}
