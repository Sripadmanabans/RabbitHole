import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("org.jetbrains.kotlin.plugin.serialization")
}

base.archivesBaseName = "models"

kotlin {
  jvm().compilations.getByName("main") {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
      )
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(deps.kotlin.stdLib.common)
        implementation(deps.kotlin.serialization.core)
      }
    }
    jvmMain {
      dependencies {
        implementation(deps.kotlin.stdLib.jdk)
      }
    }
  }
}

val NamedDomainObjectContainer<KotlinSourceSet>.jvmMain: NamedDomainObjectProvider<KotlinSourceSet>
  get() = named<KotlinSourceSet>("jvmMain")
