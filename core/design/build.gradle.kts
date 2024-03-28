plugins {
    alias(libs.plugins.xp.android.library)
    alias(libs.plugins.xp.android.library.compose)
    alias(libs.plugins.xp.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "gay.pyrrha.dailyxp.core.design"
    defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

dependencies {
    lintPublish(projects.lint)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundationLayout)
    api(libs.androidx.compose.materialIconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.uiUtil)
    api(projects.core.model)
    api(projects.core.util)
    api(projects.i18n)

    testImplementation(libs.androidx.compose.uiTest)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
//    testImplementation(projects.core.screenshotTesting)
//    testImplementation(projects.core.testing)

    androidTestImplementation(libs.androidx.compose.uiTest)
//    androidTestImplementation(projects.core.testing)
}
