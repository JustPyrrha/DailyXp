rootProject.name = "buildLogic"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        val libs by creating {
            from(files("../libs.versions.toml"))
        }
    }
}
