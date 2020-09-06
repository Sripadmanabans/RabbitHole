package com.adjectivemonk2.rabbithole

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.adjectivemonk2.rabbithole.ui.RabbitHoleTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.coil.CoilImageWithCrossfade

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RabbitHoleTheme {
        val viewModel: MainViewModel = viewModel(factory = defaultViewModelProviderFactory)
        val collectAsState = viewModel.comicDataWrapper.collectAsState()
        SampleImage(url = collectAsState.value)
      }
    }
  }
}

@Composable
fun SampleImage(url: String) {
  CoilImageWithCrossfade(data = url, modifier = Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
fun PreviewImage() {
  RabbitHoleTheme {
    SampleImage("http://i.annihil.us/u/prod/marvel/i/mg/c/80/5e3d7536c8ada.jpg")
  }
}
