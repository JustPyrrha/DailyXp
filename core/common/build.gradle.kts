plugins {
    alias(libs.plugins.xp.android.library)
    alias(libs.plugins.xp.android.library.jacoco)
    alias(libs.plugins.xp.android.hilt)
}

android {
    namespace = "gay.pyrrha.dailyxp.core.common"
}

dependencies {
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
