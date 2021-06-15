package com.xmartlabs.gong

import java.util.Locale

/**
 * Created by mirland on 28/04/20.
 */
object Config {
  const val PROD: Boolean = "dev" != BuildConfig.FLAVOR &&
      "qa" != BuildConfig.FLAVOR &&
      "staging" != BuildConfig.FLAVOR

  val DEBUG: Boolean = BuildConfig.DEBUG

  val ANDROID_SYSTEM_LOG_ENABLED = DEBUG || !PROD
  val CRASHLYTICS_LOG_ENABLED = !DEBUG && BuildConfig.CRASHLYTICS_ENABLED

  val APP_SETTINGS_SHARED_PREFERENCES_NAME = BuildConfig.APP_NAME.toFileName() + "_settings"
  val DB_NAME = BuildConfig.APP_NAME.toFileName()

  const val API_BASE_URL = BuildConfig.API_BASE_URL
}

private fun String.toFileName() = trim()
    .replace(" +", " ")
    .lowercase(Locale.ROOT)
    .replace(" ", "_")
