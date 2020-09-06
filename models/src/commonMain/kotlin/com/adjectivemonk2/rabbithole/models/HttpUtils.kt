package com.adjectivemonk2.rabbithole.models

fun String.convertToHttps(): String {
  return replace("http:", "https:")
}
