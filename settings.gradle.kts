pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "book-search"
include(":app")
include(":core:ui")
include(":domain")
include(":data")
include(":feature:home")
include(":feature:favorite")
include(":feature:library")
include(":feature:book")
include(":feature:region")
include(":feature:discovery")
