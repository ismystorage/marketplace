pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

rootProject.name = "marketplace"

includeBuild("lessons")
includeBuild("delivery-app")
