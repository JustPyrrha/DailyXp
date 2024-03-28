rootProject.name = "DailyXP"

pluginManagement {
    includeBuild("buildLogic")
    repositories {
        google()
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
    versionCatalogs {
        val libs by creating {
            from(files("libs.versions.toml"))
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":app",

    ":core:common",
    ":core:data",
    ":core:datastore",
    ":core:datastore-proto",
    ":core:design",
    ":core:model",
    ":core:ui",
    ":core:util",

    ":feature:more",
    ":feature:onboarding",
    ":feature:quests",

    ":i18n",
    ":lint"
)
