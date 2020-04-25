package com.xmartlabs.template

import android.app.Application
import android.os.Build
import android.os.StrictMode
import coil.Coil
import coil.ImageLoaderBuilder
import coil.util.DebugLogger
import com.xmartlabs.template.device.di.RepositoryDiModuleProvider
import com.xmartlabs.template.device.di.UseCaseDiModule
import com.xmartlabs.template.device.di.ViewModelDiModule
import jonathanfinerty.once.Once
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by mirland on 25/04/20.
 */
class AppBase : Application() {
  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      setupStrictMode()
    }
    setupCrashlytics()
    setupKoinModules()
    setupLoggers()
    setupCoil()
    setupOnce()
  }

  private fun setupOnce() = Once.initialise(this)

  private fun setupLoggers() {
    // TODO: Initialize timber
    // TODO: Initialize Stetho
  }

  private fun setupCrashlytics() {
    // TODO:
  }

  private fun setupKoinModules() {
    startKoin {
      androidContext(this@AppBase)
      modules(
        RepositoryDiModuleProvider.repositories,
        UseCaseDiModule.useCases,
        ViewModelDiModule.viewModels
      )
    }
  }

  private fun setupCoil() {
    val imageLoader = ImageLoaderBuilder(this)
      .apply { if (BuildConfig.DEBUG) logger(DebugLogger()) }
      .build()
    Coil.setImageLoader(imageLoader)
  }

  @SuppressWarnings("MagicNumber")
  private fun setupStrictMode() {
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
      if (Build.VERSION.SDK_INT >= 23) {
        detectCleartextNetwork()
      }
      if (Build.VERSION.SDK_INT >= 26) {
        detectContentUriWithoutPermission()
      }
      penaltyLog()
    }.build())
  }
}
