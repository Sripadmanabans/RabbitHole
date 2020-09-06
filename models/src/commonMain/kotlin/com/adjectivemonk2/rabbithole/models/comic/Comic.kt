package com.adjectivemonk2.rabbithole.models.comic

import com.adjectivemonk2.rabbithole.models.Image
import com.adjectivemonk2.rabbithole.models.TextObject
import com.adjectivemonk2.rabbithole.models.Url
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comic(
  @SerialName("id") val id: Int,
  @SerialName("digitalId") val digitalId: Int,
  @SerialName("title") val title: String,
  @SerialName("issueNumber") val issueNumber: Double,
  @SerialName("variantDescription") val variantDescription: String,
  @SerialName("description") val description: String?,
  @SerialName("modified") val modified: String,
  @SerialName("format") val format: String,
  @SerialName("pageCount") val pageCount: Int,
  @SerialName("textObjects") val textObjects: List<TextObject>,
  @SerialName("resourceURI") val resourceUri: String,
  @SerialName("urls") val urls: List<Url>,
  @SerialName("images") val images: List<Image>
)
