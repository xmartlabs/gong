package com.xmartlabs.template

import android.app.Application
import android.os.Build
import android.os.StrictMode
import coil.Coil
import coil.ImageLoaderBuilder
import coil.util.DebugLogger
import com.facebook.stetho.Stetho
import com.xmartlabs.template.device.di.NetworkDiModule
import com.xmartlabs.template.device.di.RepositoryDiModuleProvider
import com.xmartlabs.template.device.di.UseCaseDiModule
import com.xmartlabs.template.device.di.ViewModelDiModule
import jonathanfinerty.once.Once
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by mirland on 25/04/20.
 */
class AppBase : Application() {
  override fun onCreate() {
    super.onCreate()

    if (Config.DEBUG) {
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
    if (Config.ANDROID_SYSTEM_LOG_ENABLED) {
      Timber.plant(Timber.DebugTree())
    }
    if (Config.STETHO_ENABLED) {
      // TODO: create a crashlytics three to share data in Stetho
      Stetho.initializeWithDefaults(this)
    }
  }

  private fun setupCrashlytics() {
    // TODO:
  }

  private fun setupKoinModules() {
    startKoin {
      androidContext(this@AppBase)
      modules(
        NetworkDiModule.network,
        RepositoryDiModuleProvider.repositories,
        RepositoryDiModuleProvider.sources,
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
