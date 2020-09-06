package com.adjectivemonk2.rabbithole.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Url(@SerialName("type") val type: String, @SerialName("url") val httpUrl: String)
