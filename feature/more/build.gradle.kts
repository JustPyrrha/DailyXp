import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.xp.android.feature)
    alias(libs.plugins.xp.android.library.compose)
    alias(libs.plugins.xp.android.library.jacoco)
    alias(libs.plugins.grgit)
}

android {
    namespace = "gay.pyrrha.dailyxp.feature.more"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "COMMIT_SHA", "\"${grgitService.service.get().grgit.describe()}\"")
        buildConfigField("String", "COMMIT_COUNT", "\"${grgitService.service.get().grgit.log().count()}\"")
        buildConfigField("String", "BUILD_TIME", "\"${OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME)}\"")
    }
}


dependencies {
    api(libs.androidx.navigation.compose)
    api(libs.google.material)
    api(projects.core.data)
    api(projects.core.model)
    api(projects.core.design)
    api(projects.i18n)

    implementation(libs.aboutLicenses.composeMaterial3)
}
