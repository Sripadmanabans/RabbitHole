plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.kapt")
  id("org.jetbrains.kotlin.plugin.serialization")
  id("dagger.hilt.android.plugin")
}

android {
  compileSdkVersion(androidConfig.compileSdk)

  defaultConfig {
    minSdkVersion(androidConfig.minSdk)
    targetSdkVersion(androidConfig.targetSdk)
    versionCode = androidConfig.version.code
    versionName = androidConfig.version.fullName

    @Suppress("LocalVariableName") val RABBIT_HOLE_PUBLIC_KEY: String by project
    @Suppress("LocalVariableName") val RABBIT_HOLE_PRIVATE_KEY: String by project
    buildConfigField("String", "PUBLIC_KEY", "\"$RABBIT_HOLE_PUBLIC_KEY\"")
    buildConfigField("String", "PRIVATE_KEY", "\"$RABBIT_HOLE_PRIVATE_KEY\"")
  }
  signingConfigs {

    getByName("debug") {
      storeFile = file("keystore/debug.keystore")
      storePassword = "rabbithole"
      keyAlias = "rabbithole"
      keyPassword = "rabbithole"
    }

    if (file("keystore/upload.keystore").exists()) {
      @Suppress("LocalVariableName") val RABBIT_HOLE_STORE_PASSWORD: String by project
      @Suppress("LocalVariableName") val RABBIT_HOLE_KEY_ALIAS: String by project
      @Suppress("LocalVariableName") val RABBIT_HOLE_KEY_PASSWORD: String by project
      getByName("upload") {
        storeFile = file("keystore/upload.keystore")
        storePassword = RABBIT_HOLE_STORE_PASSWORD
        keyAlias = RABBIT_HOLE_KEY_ALIAS
        keyPassword = RABBIT_HOLE_KEY_PASSWORD
      }
    }
  }

  buildTypes {

    getByName("debug") {
      applicationIdSuffix = ".debug"
      signingConfig = signingConfigs.getByName("debug")
    }

    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + listOf(
      "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
      "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    )
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerVersion = versions.kotlin.runtime
    kotlinCompilerExtensionVersion = versions.androidX.compose
  }
}

dependencies {

  implementation(project(":models"))

  implementation(deps.kotlin.stdLib.jdk)
  implementation(deps.kotlin.serialization.core)

  implementation(deps.androidX.core)
  implementation(deps.androidX.activity)
  implementation(deps.androidX.appcompat)
  implementation(deps.google.material)

  implementation(deps.androidX.compose.compiler)

  implementation(deps.androidX.compose.animation.animation)
  implementation(deps.androidX.compose.animation.core)

  implementation(deps.androidX.compose.foundation.foundation)
  implementation(deps.androidX.compose.foundation.layout)
  implementation(deps.androidX.compose.foundation.text)

  implementation(deps.androidX.compose.material.material)
  implementation(deps.androidX.compose.material.icons.core)
  implementation(deps.androidX.compose.material.icons.extended)

  implementation(deps.androidX.compose.runtime.runtime)
  implementation(deps.androidX.compose.runtime.dispatch)
  implementation(deps.androidX.compose.runtime.livedata)
  implementation(deps.androidX.compose.runtime.savedInstanceState)

  implementation(deps.androidX.compose.ui.ui)
  implementation(deps.androidX.compose.ui.geometry)
  implementation(deps.androidX.compose.ui.graphics)
  implementation(deps.androidX.compose.ui.text)
  implementation(deps.androidX.compose.ui.textAndroid)
  implementation(deps.androidX.ui.tooling)
  implementation(deps.androidX.compose.ui.unit)
  implementation(deps.androidX.compose.ui.util)
  implementation(deps.androidX.compose.ui.viewbinding)

  implementation(deps.chris.coil)

  implementation(deps.jake.timber.android)

  implementation(deps.google.hilt.runtime)
  kapt(deps.google.hilt.compiler)

  implementation(deps.androidX.hilt.common)
  implementation(deps.androidX.hilt.viewModel)
  kapt(deps.androidX.hilt.compiler)

  implementation(deps.square.okhttp.client)
  implementation(deps.square.okhttp.logging)

  implementation(deps.square.retrofit.client)
  implementation(deps.jake.converter)

  implementation(deps.jake.byteunits)

  implementation(deps.androidX.lifecycle.liveData)
  implementation(deps.androidX.lifecycle.runtime)
  implementation(deps.androidX.lifecycle.viewmodel)
}
