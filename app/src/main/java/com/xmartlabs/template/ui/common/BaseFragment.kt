package com.xmartlabs.template.ui.common

import androidx.fragment.app.Fragment

/**
 * Created by mirland on 25/04/20.
 */
abstract class BaseFragment : Fragment() {
  protected val router: NavRouter by lazy { FragmentNavRouter(this) }
}
