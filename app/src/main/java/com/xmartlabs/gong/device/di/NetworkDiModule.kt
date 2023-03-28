package com.xmartlabs.gong.device.di

import com.xmartlabs.gong.Config
import com.xmartlabs.gong.data.service.NetworkLayerCreator
import com.xmartlabs.gong.data.service.ProjectServiceApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by mirland on 28/04/20.
 */
object NetworkDiModule {
    @Suppress("RemoveExplicitTypeArguments")
    val network = module {
        single { get<Retrofit>().create(ProjectServiceApi::class.java) }
        single<OkHttpClient.Builder> {
            val sessionInterceptors =
                listOf<Interceptor>(Interceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("apikey", Config.SUPABASE_API_KEY)
                        .build()
                    chain.proceed(request)
                }) // TODO: Add session interceptor and refresh token interceptor
            val networkInterceptorsInjectors = getAll<NetworkLoggingInterceptorInjector>()
            NetworkLayerCreator.createOkHttpClientBuilder(sessionInterceptors, networkInterceptorsInjectors)
        }
        single<OkHttpClient> { get<OkHttpClient.Builder>().build() }
        single {
            NetworkLayerCreator.createRetrofitInstance(Config.API_BASE_URL, get())
        }
    }
}

// There are some libraries that adds the interceptor manually
// ie: https://docs.bugsee.com/sdk/android/network/
fun interface NetworkLoggingInterceptorInjector {
    fun injectNetworkInterceptor(builder: OkHttpClient.Builder)
}
