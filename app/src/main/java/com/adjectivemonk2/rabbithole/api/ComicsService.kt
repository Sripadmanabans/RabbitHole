package com.adjectivemonk2.rabbithole.api

import com.adjectivemonk2.rabbithole.models.comic.ComicDataWrapper
import retrofit2.http.GET

interface ComicsService {

  @GET("/v1/public/comics")
  suspend fun getComics(): ComicDataWrapper
}
