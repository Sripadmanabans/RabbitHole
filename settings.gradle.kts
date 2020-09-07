rootProject.name = "RabbitHole"

listOf(
  ":app",
  ":models"
).forEach { include(it) }

enableFeaturePreview("GRADLE_METADATA")
