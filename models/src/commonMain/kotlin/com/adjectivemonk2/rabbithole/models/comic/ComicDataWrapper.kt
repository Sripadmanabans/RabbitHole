package com.adjectivemonk2.rabbithole.models.comic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicDataWrapper(
  @SerialName("code") val code: Int,
  @SerialName("status") val status: String,
  @SerialName("copyright") val copyright: String,
  @SerialName("attributionText") val attributionText: String,
  @SerialName("attributionHTML") val attributionHtml: String,
  @SerialName("data") val data: ComicDataContainer,
  @SerialName("etag") val eTag: String
)
