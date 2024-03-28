plugins {
    alias(libs.plugins.xp.android.library)
    alias(libs.plugins.xp.android.library.compose)
    alias(libs.plugins.xp.android.library.jacoco)
}

android {
    namespace = "gay.pyrrha.dailyxp.core.ui"
    defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

dependencies {
    api(libs.androidx.appcompat)
    api(projects.core.design)
    api(projects.core.model)

    implementation(projects.i18n)
}
