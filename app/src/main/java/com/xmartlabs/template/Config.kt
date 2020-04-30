package com.xmartlabs.template

/**
 * Created by mirland on 28/04/20.
 */
object Config {
  const val PROD: Boolean = "dev" != BuildConfig.FLAVOR &&
      "qa" != BuildConfig.FLAVOR &&
      "staging" != BuildConfig.FLAVOR

  val DEBUG: Boolean = BuildConfig.DEBUG

  val ANDROID_SYSTEM_LOG_ENABLED = DEBUG || !PROD

  val STETHO_ENABLED = DEBUG || !PROD

  val API_BASE_URL = BuildConfig.API_BASE_URL
}
