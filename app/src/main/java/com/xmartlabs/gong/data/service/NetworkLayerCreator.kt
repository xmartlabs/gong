package com.xmartlabs.gong.data.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.xmartlabs.gong.device.di.NetworkLoggingInterceptorInjector
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Created by mirland on 28/04/20.
 */
object NetworkLayerCreator {
    @SuppressWarnings("MagicNumber")
    private val HTTP_CONNECT_TIMEOUT = 20.toDuration(DurationUnit.SECONDS)

    @SuppressWarnings("MagicNumber")
    private val HTTP_READ_TIMEOUT = 20.toDuration(DurationUnit.SECONDS)
    private val JSON_MEDIA_TYPE = "application/json".toMediaType()

    fun createOkHttpClientBuilder(
        sessionInterceptors: List<Interceptor>,
        networkLoggingInterceptorInjectors: List<NetworkLoggingInterceptorInjector>,
    ) = OkHttpClient.Builder()
        .connectTimeout(HTTP_CONNECT_TIMEOUT.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        .readTimeout(HTTP_READ_TIMEOUT.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        .also { builder ->
            sessionInterceptors.forEach { interceptor -> builder.addNetworkInterceptor(interceptor) }
            networkLoggingInterceptorInjectors.forEach { injector -> injector.injectNetworkInterceptor(builder) }
        }

    fun createRetrofitInstance(
        baseUrl: String,
        httpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(createSerializerConverterFactory())
        .client(httpClient)
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    private fun createSerializerConverterFactory() = Json { ignoreUnknownKeys = true }
        .asConverterFactory(JSON_MEDIA_TYPE)
}
