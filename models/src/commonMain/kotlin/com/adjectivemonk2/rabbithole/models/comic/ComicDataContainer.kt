package com.adjectivemonk2.rabbithole.models.comic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicDataContainer(
  @SerialName("offset") val offset: Int,
  @SerialName("limit") val limit: Int,
  @SerialName("total") val total: Int,
  @SerialName("count") val count: Int,
  @SerialName("results") val results: List<Comic>
)
