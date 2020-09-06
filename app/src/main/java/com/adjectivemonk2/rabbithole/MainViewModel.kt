package com.adjectivemonk2.rabbithole

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adjectivemonk2.rabbithole.api.ComicsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.warn
import java.io.IOException

class MainViewModel @ViewModelInject constructor(
  private val comicsService: ComicsService
) : ViewModel() {

  private val _comicWrapper = MutableStateFlow("")
  val comicDataWrapper: StateFlow<String> = _comicWrapper

  init {
    viewModelScope.launch {
      try {
        val comics = comicsService.getComics()
        val url =
          comics.data.results.find { it.images.isNotEmpty() }?.images?.first()?.url.orEmpty()
        _comicWrapper.value = url
      } catch (ioException: IOException) {
        Timber.warn(ioException) { "Call failed" }
      }
    }
  }
}
