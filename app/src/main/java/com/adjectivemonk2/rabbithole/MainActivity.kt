package com.adjectivemonk2.rabbithole

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import androidx.ui.tooling.preview.Preview
import com.adjectivemonk2.rabbithole.api.ComicsService
import com.adjectivemonk2.rabbithole.ui.RabbitHoleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.warn
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject internal lateinit var comicsService: ComicsService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RabbitHoleTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          Greeting("Android")
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    lifecycleScope.launch {
      try {
        comicsService.getComics()
      } catch (ioException: IOException) {
        Timber.warn(ioException) { "Call failed" }
      }
    }
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  RabbitHoleTheme {
    Greeting("Android")
  }
}
