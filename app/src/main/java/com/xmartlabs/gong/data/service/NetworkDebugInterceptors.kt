package com.xmartlabs.gong.data.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Created by mirland on 28/04/20.
 */
object NetworkDebugInterceptors {
  const val OK_HTTP_INTERCEPTOR_LOGGER_TAG = "OkHttp"
  const val OK_2_CURL_INTERCEPTOR_LOGGER_TAG = "Ok2Curl"
  private const val UNKNOWN_CHAR = '�'

  fun createDebugInterceptors(
      useOkHttpInterceptor: Boolean,
      useCurlInterceptor: Boolean,
      useStethoInterceptor: Boolean
  ): List<Interceptor> = mutableListOf<Interceptor>()
      .apply {
        if (useStethoInterceptor) add(createStethoInterceptor())
        if (useCurlInterceptor) add(createCurlInterceptor())
        if (useOkHttpInterceptor) add(createOkHttpInterceptor())
      }

  private fun createCurlInterceptor() = CurlInterceptor(Loggable { message ->
    Timber.tag(OK_2_CURL_INTERCEPTOR_LOGGER_TAG)
        .d(message.sanitize())
  })

  private fun createOkHttpInterceptor() = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) = Timber.tag(OK_HTTP_INTERCEPTOR_LOGGER_TAG).d(message.sanitize())
  }).apply { level = HttpLoggingInterceptor.Level.BODY }

  private fun createStethoInterceptor() = StethoInterceptor()

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
