package com.adjectivemonk2.rabbithole.network.okhttp

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

internal class AuthInterceptor @Inject constructor(
  private val queryParams: Provider<QueryParams>
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val oldRequest = chain.request()
    val (publicKey, timeStamp, hash) = queryParams.get()
    val newUrl = oldRequest.url.newBuilder().run {
      addQueryParameter(TIME_STAMP, timeStamp.toString())
      addQueryParameter(API_KEY, publicKey)
      addQueryParameter(HASH, hash)
      build()
    }
    val newRequest = oldRequest.newBuilder().url(newUrl).build()
    return chain.proceed(newRequest)
  }

  companion object {
    private const val API_KEY = "apikey"
    private const val HASH = "hash"
    private const val TIME_STAMP = "ts"
  }
}

data class QueryParams(val publicKey: String, val timeStamp: Long, val hash: String)
