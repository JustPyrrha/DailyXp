plugins {
    alias(libs.plugins.xp.android.feature)
    alias(libs.plugins.xp.android.library.compose)
    alias(libs.plugins.xp.android.library.jacoco)
    alias(libs.plugins.xp.android.hilt)
}

android {
    namespace = "gay.pyrrha.dailyxp.feature.onboarding"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.i18n)

    implementation(libs.materialMotion)
}
