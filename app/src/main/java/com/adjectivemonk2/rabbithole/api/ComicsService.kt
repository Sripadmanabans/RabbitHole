package com.adjectivemonk2.rabbithole.api

import retrofit2.http.GET

interface ComicsService {

  @GET("/v1/public/comics")
  suspend fun getComics()
}
