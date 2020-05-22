package com.xmartlabs.gong.device.logger

import android.util.Log
import com.facebook.stetho.inspector.console.CLog
import com.facebook.stetho.inspector.console.ConsolePeerManager
import com.facebook.stetho.inspector.protocol.module.Console
import com.xmartlabs.gong.data.service.NetworkDebugInterceptors
import timber.log.Timber
import java.util.Date

/**
 * Created by mirland on 02/05/20.
 */
class StethoTimberTree : Timber.Tree() {
  companion object {
    private val EXCLUDED_TAGS = listOf(
        NetworkDebugInterceptors.OK_HTTP_INTERCEPTOR_LOGGER_TAG,
        NetworkDebugInterceptors.OK_2_CURL_INTERCEPTOR_LOGGER_TAG
    )
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (ConsolePeerManager.getInstanceOrNull() == null || tag in EXCLUDED_TAGS) {
      return
    }
    val stethoLogLevel: Console.MessageLevel = when (priority) {
      Log.VERBOSE, Log.DEBUG -> Console.MessageLevel.DEBUG
      Log.INFO -> Console.MessageLevel.LOG
      Log.WARN -> Console.MessageLevel.WARNING
      Log.ERROR, Log.ASSERT -> Console.MessageLevel.ERROR
      else -> Console.MessageLevel.LOG
    }

    val errorMessage = Log.getStackTraceString(t)
    val prettyMessage = "${Date()}:$tag -> $message${if (errorMessage.isBlank()) "" else "\n$errorMessage"}"
    CLog.writeToConsole(stethoLogLevel, Console.MessageSource.OTHER, prettyMessage)
  }
}
