package com.adjectivemonk2.rabbithole.network.okhttp

import android.app.Application
import com.adjectivemonk2.rabbithole.qualifier.PrivateKey
import com.adjectivemonk2.rabbithole.qualifier.PublicKey
import com.jakewharton.byteunits.BinaryByteUnit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import timber.log.verbose
import java.io.File
import java.security.MessageDigest
import java.time.Instant

@Module
@InstallIn(ApplicationComponent::class)
internal object OkhttpModule {

  private const val MB_BYTES = 10L

  @Provides fun cache(application: Application): Cache {
    val cacheDir = application.cacheDir / "http"
    return Cache(cacheDir, BinaryByteUnit.MEBIBYTES.toBytes(MB_BYTES))
  }

  @Provides fun loggingInterceptor(): HttpLoggingInterceptor {
    val taggedTree = Timber.tagged("http")
    return HttpLoggingInterceptor(
      object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
          taggedTree.verbose { message }
        }
      }
    ).apply { level = HttpLoggingInterceptor.Level.BODY }
  }

  @Provides fun okhttp(
    cache: Cache,
    loggingInterceptor: HttpLoggingInterceptor,
    authInterceptor: AuthInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder().run {
      addInterceptor(authInterceptor)
      addInterceptor(loggingInterceptor)
      cache(cache)
      build()
    }
  }

  @Provides fun queryParams(
    @PublicKey publicKey: String,
    @PrivateKey privateKey: String
  ): QueryParams {
    val md5 = MessageDigest.getInstance("MD5")
    val timeStamp = Instant.now().toEpochMilli()
    val keyArray = "$timeStamp$privateKey$publicKey".toByteArray()
    val digest = md5.digest(keyArray)
    return QueryParams(publicKey, timeStamp, digest.toHexString())
  }

  private fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

  private operator fun File.div(pathSegment: String) = File(this, pathSegment)
}
