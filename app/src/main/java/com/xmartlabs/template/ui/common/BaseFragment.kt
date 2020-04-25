package com.xmartlabs.template.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by mirland on 25/04/20.
 */
abstract class BaseFragment<V : ViewBinding> : Fragment() {
  private var _binding: V? = null
  protected val viewBinding get() = _binding!!

  protected abstract fun inflateViewBinding(): V

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    _binding = inflateViewBinding()
    return viewBinding.root
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

  protected inline fun withViewBinding(block: V.() -> Unit) = with(viewBinding, block)
}
