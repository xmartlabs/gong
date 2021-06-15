package com.xmartlabs.gong

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.moczul.ok2curl.CurlInterceptor
import com.xmartlabs.gong.device.di.NetworkLoggingInterceptorInjector
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Created by mirland on 15/6/21.
 */
object NetworkDebugInterceptors {
  private const val OK_HTTP_INTERCEPTOR_LOGGER_TAG = "OkHttp"
  private const val OK_2_CURL_INTERCEPTOR_LOGGER_TAG = "Ok2Curl"
  private const val UNKNOWN_CHAR = '�'

   fun createFlipperInterceptor(networkFlipperPlugin: NetworkFlipperPlugin): Interceptor =
      FlipperOkhttpInterceptor(networkFlipperPlugin)

   fun createCurlInterceptor() = CurlInterceptor { message ->
    Timber.tag(OK_2_CURL_INTERCEPTOR_LOGGER_TAG)
        .d(message.sanitize())
  }

   fun createOkHttpInterceptor() = HttpLoggingInterceptor { message ->
    Timber.tag(OK_HTTP_INTERCEPTOR_LOGGER_TAG)
        .d(message.sanitize())
  }.apply { level = HttpLoggingInterceptor.Level.BODY }

  private fun String.sanitize(): String {
    val indexOfUnknownChar = indexOf(UNKNOWN_CHAR)
    return if (indexOfUnknownChar >= 0) {
      val lastIndexOfUnknownChar = indexOfLast { it == UNKNOWN_CHAR }
      StringBuilder(this)
          .replace(indexOfUnknownChar, lastIndexOfUnknownChar + 1, "** Suppress file data **")
          .toString()
    } else {
      this
    }
  }
}

@JvmInline
value class DebugNetworkLoggingInterceptorInjector(
    private val interceptor: Interceptor,
) : NetworkLoggingInterceptorInjector {
  override fun injectNetworkInterceptor(builder: OkHttpClient.Builder) {
    builder.addNetworkInterceptor(interceptor)
  }
}
