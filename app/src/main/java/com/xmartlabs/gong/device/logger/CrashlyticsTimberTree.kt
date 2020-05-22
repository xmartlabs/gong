package com.xmartlabs.gong.device.logger

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

/**
 * Created by mirland on 02/05/20.
 */
class CrashlyticsTimberTree : Timber.Tree() {
  companion object {
    private const val CRASHLYTICS_KEY_PRIORITY = "priority"
    private const val CRASHLYTICS_KEY_TAG = "tag"
    private const val CRASHLYTICS_KEY_MESSAGE = "message"
    private val LOG_PRIORITIES = setOf(Log.WARN, Log.ERROR, Log.ASSERT)
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (priority in LOG_PRIORITIES) {
      FirebaseCrashlytics.getInstance().run {
        setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)
        setCustomKey(CRASHLYTICS_KEY_TAG, tag ?: "")
        setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
        recordException(t ?: Exception(message))
      }
    }
  }
}
