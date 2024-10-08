plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "ru.otus.otuskotlin.marketplace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}

tasks {
    create("check") {
        group = "verification"
        dependsOn(gradle.includedBuild("delivery-app").task(":check"))
    }
}
