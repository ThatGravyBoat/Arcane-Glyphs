enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Glyphs"

pluginManagement {
    repositories {
        maven(url = "https://maven.neoforged.net/releases/")
        gradlePluginPortal()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}