package com.xmartlabs.template.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.common.ActivityNavRouter
import com.xmartlabs.template.ui.common.NavRouter

class MainActivity(
    private val router: ActivityNavRouter = ActivityNavRouter()
) : AppCompatActivity(), NavRouter by router {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    router.setupNavController(this, R.id.nav_host_fragment_container)
  }
}
