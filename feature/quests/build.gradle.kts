plugins {
    alias(libs.plugins.xp.android.feature)
    alias(libs.plugins.xp.android.library.compose)
    alias(libs.plugins.xp.android.hilt)
}

android {
    namespace = "gay.pyrrha.dailyxp.feature.quests"
}

dependencies {
    api(libs.androidx.compose.material3)
    api(projects.i18n)
    api(projects.core.ui)
    api(projects.core.data)
}
