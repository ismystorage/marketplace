rootProject.name = "order-linker-be"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":order-linker-order-api-v1-jackson")
include(":order-linker-item-api-v1-jackson")
include(":order-linker-api-v1-mappers")
include(":order-linker-common")