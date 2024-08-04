plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "ru.otus.otuskotlin.marketplace"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

tasks {
    create("build") {
        group = "build"
        dependsOn(project(":order-linker").getTasksByName("build",false))
    }
    create("check") {
        group = "verification"
        subprojects.forEach { proj ->
            println("PROJ $proj")
            proj.getTasksByName("check", false).also {
                this@create.dependsOn(it)
            }
        }
    }
}
