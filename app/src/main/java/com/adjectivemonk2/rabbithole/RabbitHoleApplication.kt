package com.adjectivemonk2.rabbithole

import android.app.Application
import com.adjectivemonk2.rabbithole.timber.LogcatTree
import timber.log.Timber
import timber.log.verbose

// TODO Need to remove suppression after this[https://github.com/Sripadmanabans/RabbitHole/issues/3]
@Suppress("unused")
class RabbitHoleApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    Timber.plant(LogcatTree())
    Timber.verbose { "onCreate" }
  }
}
