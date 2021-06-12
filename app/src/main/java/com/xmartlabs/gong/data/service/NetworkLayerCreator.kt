package com.xmartlabs.gong.data.service

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

/**
 * Created by mirland on 28/04/20.
 */
object NetworkLayerCreator {
  @SuppressWarnings("MagicNumber")
  private val HTTP_CONNECT_TIMEOUT = Duration.seconds(20)

  @SuppressWarnings("MagicNumber")
  private val HTTP_READ_TIMEOUT = Duration.seconds(20)
  private const val API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

  private fun createOkHttpClient(interceptors: List<Interceptor>) = OkHttpClient.Builder()
      .connectTimeout(HTTP_CONNECT_TIMEOUT.inWholeMilliseconds, TimeUnit.MILLISECONDS)
      .readTimeout(HTTP_READ_TIMEOUT.inWholeMilliseconds, TimeUnit.MILLISECONDS)
      .apply { interceptors.forEach { interceptor -> addNetworkInterceptor(interceptor) } }
      .build()

  fun createRetrofitInstance(
      baseUrl: String,
      interceptors: List<Interceptor>,
  ): Retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(createGsonConverterFactory())
      .client(createOkHttpClient(interceptors))
      .build()

  private fun createGsonConverterFactory() = GsonBuilder()
      .setDateFormat(API_DATE_FORMAT)
      .create()
      .let { GsonConverterFactory.create(it) }
}
