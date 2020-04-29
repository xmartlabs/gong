package com.xmartlabs.template.device.di

import com.xmartlabs.template.Config
import com.xmartlabs.template.data.service.LocationServiceApi
import com.xmartlabs.template.data.service.NetworkDebugInterceptors
import com.xmartlabs.template.data.service.NetworkLayerCreator
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
      val debugInterceptors = NetworkDebugInterceptors.createDebugInterceptors(
        useOkHttpInterceptor = Config.ANDROID_SYSTEM_LOG_ENABLED,
        useCurlInterceptor = Config.ANDROID_SYSTEM_LOG_ENABLED,
        useStethoInterceptor = Config.STETHO_ENABLED
      )
      val sessionInterceptors = listOf<Interceptor>() // TODO: Add session interceptor and refresh token interceptor
      NetworkLayerCreator.createRetrofitInstance(Config.API_BASE_URL, sessionInterceptors + debugInterceptors)
    }
  }
}
