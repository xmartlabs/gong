package com.xmartlabs.template.device.logger

import android.content.Context
import com.facebook.stetho.Stetho
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.xmartlabs.template.Config
import timber.log.Timber

/**
 * Created by mirland on 02/05/20.
 */
object LoggerModule {
  private var initialized = false

  @Synchronized
  fun initializeModule(context: Context) {
    if (!initialized) {
      initialized = true
      if (Config.ANDROID_SYSTEM_LOG_ENABLED) {
        Timber.plant(Timber.DebugTree())
      }
      if (Config.STETHO_ENABLED) {
        Stetho.initializeWithDefaults(context)
        Timber.plant(StethoTimberTree())
      }
      if (Config.CRASHLYTICS_LOG_ENABLED) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        Timber.plant(CrashlyticsTimberTree())
      }
    }
  }
}
