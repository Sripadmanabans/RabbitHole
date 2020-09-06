package com.adjectivemonk2.rabbithole.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
  @SerialName("path") val path: String,
  @SerialName("extension") val extension: String
) {
  val url: String
    get() = "$path.$extension".convertToHttps()
}
