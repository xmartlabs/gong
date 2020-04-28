package com.xmartlabs.template.device.di

import com.xmartlabs.template.Config
import com.xmartlabs.template.data.service.NetworkDebugInterceptors
import com.xmartlabs.template.data.service.NetworkLayerCreator
import com.xmartlabs.template.data.service.LocationServiceApi
import okhttp3.Interceptor
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by mirland on 28/04/20.
 */
object NetworkDiModule {
  val network = module {
    single { get<Retrofit>().create(LocationServiceApi::class.java) }
    single {
      val interceptors = mutableListOf<Interceptor>()
        .apply {
          if (Config.STETHO_ENABLED) {
            add(NetworkDebugInterceptors.createStethoInterceptor())
          }
          if (Config.ANDROID_SYSTEM_LOG_ENABLED) {
            add(NetworkDebugInterceptors.createCurlInterceptor())
            add(NetworkDebugInterceptors.createOhHttpInterceptor())
          }
        }

      NetworkLayerCreator.createRetrofitInstance(Config.API_BASE_URL, interceptors)
    }
  }
}
