package com.adjectivemonk2.rabbithole.network.retrofit

import com.adjectivemonk2.rabbithole.api.ComicsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal object RetrofitModule {

  @Provides fun baseUrl(): HttpUrl = "https://gateway.marvel.com".toHttpUrl()

  @Provides fun json(): Json {
    return Json {
      isLenient = true
      ignoreUnknownKeys = true
    }
  }

  @Provides fun converterFactory(json: Json): Converter.Factory {
    val contentType = "application/json".toMediaType()
    return json.asConverterFactory(contentType)
  }

  @Singleton @Provides fun retrofit(
    baseUrl: HttpUrl,
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory
  ): Retrofit {
    return Retrofit.Builder().run {
      baseUrl(baseUrl)
      client(okHttpClient)
      addConverterFactory(converterFactory)
      build()
    }
  }

  @Singleton @Provides fun comicsService(retrofit: Retrofit): ComicsService {
    return retrofit.create()
  }
}
