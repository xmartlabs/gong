package com.xmartlabs.gong

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.xmartlabs.gong.device.di.NetworkLoggingInterceptorInjector
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

object LoggerTools {
  private val networkFlipperPlugin = NetworkFlipperPlugin()

  fun provideKoinDebugModule() = module {
    single(named("FlipperInterceptor")) {
      DebugNetworkLoggingInterceptorInjector(NetworkDebugInterceptors.createFlipperInterceptor(networkFlipperPlugin))
    } bind NetworkLoggingInterceptorInjector::class
    single(named("CurlInterceptor")) {
      DebugNetworkLoggingInterceptorInjector(NetworkDebugInterceptors.createCurlInterceptor())
    } bind NetworkLoggingInterceptorInjector::class
    single<NetworkLoggingInterceptorInjector>(named("LoggingInterceptor")) {
      DebugNetworkLoggingInterceptorInjector(NetworkDebugInterceptors.createOkHttpInterceptor())
    } bind NetworkLoggingInterceptorInjector::class
    single(createdAtStart = true) { setupFlipper(get()) }
  }

  private fun setupFlipper(context: Context): FlipperClient {
    SoLoader.init(context, false)
    return AndroidFlipperClient.getInstance(context)
        .also { client ->
          if (FlipperUtils.shouldEnableFlipper(context)) {
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.addPlugin(NavigationFlipperPlugin.getInstance())
            client.addPlugin(networkFlipperPlugin)
            client.addPlugin(DatabasesFlipperPlugin(context))
            client.addPlugin(SharedPreferencesFlipperPlugin(context))
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.start()
          }
        }
  }
}
