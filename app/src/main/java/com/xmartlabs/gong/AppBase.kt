package com.xmartlabs.gong

import android.app.Application
import android.os.Build
import android.os.StrictMode
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import com.xmartlabs.gong.device.logger.LoggerModule
import jonathanfinerty.once.Once

/**
 * Created by mirland on 25/04/20.
 */
abstract class AppBase : Application() {
  override fun onCreate() {
    super.onCreate()

    setupStrictMode()
    setupLoggers()
    setupKoinModules()
    setupCoil()
    setupOnce()
  }

  private fun setupOnce() = Once.initialise(this)

  private fun setupLoggers() = LoggerModule.initializeModule(this)

  protected abstract fun setupKoinModules()

  private fun setupCoil() {
    val imageLoader = ImageLoader.Builder(this)
        .apply { if (BuildConfig.DEBUG) logger(DebugLogger()) }
        .build()
    Coil.setImageLoader(imageLoader)
  }

  private fun setupStrictMode() {
    if (Config.DEBUG) {
      val builder = StrictMode.ThreadPolicy.Builder()
          .detectDiskReads()
          .detectDiskWrites()
          .detectNetwork()
          .penaltyLog()
      StrictMode.setThreadPolicy(builder.build())
      StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().apply {
        detectActivityLeaks()
        detectFileUriExposure()
        detectLeakedClosableObjects()
        detectLeakedRegistrationObjects()
        detectLeakedSqlLiteObjects()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          detectCleartextNetwork()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          detectContentUriWithoutPermission()
        }
        penaltyLog()
      }.build())
    }
  }
}
