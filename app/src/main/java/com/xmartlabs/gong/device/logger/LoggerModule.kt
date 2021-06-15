package com.xmartlabs.gong.device.logger

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.xmartlabs.gong.Config
import timber.log.Timber

/**
 * Created by mirland on 02/05/20.
 */
object LoggerModule {
  private var initialized = false

  @Synchronized
  fun initializeModule() {
    if (!initialized) {
      initialized = true
      if (Config.ANDROID_SYSTEM_LOG_ENABLED) {
        Timber.plant(Timber.DebugTree())
      }
      if (Config.CRASHLYTICS_LOG_ENABLED) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        Timber.plant(CrashlyticsTimberTree())
      }
    }
  }
}
