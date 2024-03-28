plugins {
    alias(libs.plugins.xp.android.library)
    alias(libs.plugins.xp.android.library.compose)
}

android {
    namespace = "gay.pyrrha.dailyxp.core.util"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
}
