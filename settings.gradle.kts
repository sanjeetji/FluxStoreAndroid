pluginManagement {
    repositories {
        // Order matters: plugin portal first helps resolve Gradle/AGP/Kotlin plugins
        gradlePluginPortal()

        // Google for Android/Compose/Google plugins
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }

        // Kotlin, Jetpack artifacts mirrored at MavenCentral too
        mavenCentral()
        // If you ever need JitPack, uncomment:
        // maven(url = "https://jitpack.io")
    }
}

dependencyResolutionManagement {
    // Recommended for AGP 8+: keep repos only here (no per-module repos)
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // If you ever need snapshot artifacts, uncomment:
        // maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        // maven(url = "https://jitpack.io")
    }
}

// Optional but nice: enables type-safe accessors for projects (not required)
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ComposeApp"
include(":app")
