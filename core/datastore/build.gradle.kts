plugins {
    alias(libs.plugins.xp.android.library)
    alias(libs.plugins.xp.android.library.jacoco)
    alias(libs.plugins.xp.android.hilt)
}

android {
    namespace = "gay.pyrrha.dailyxp.core.datastore"
    defaultConfig { consumerProguardFiles("consumer-proguard-rules.pro") }
    testOptions.unitTests.isReturnDefaultValues = true
}

dependencies {
    api(libs.androidx.datastoreCore)
    api(projects.core.datastoreProto)
    api(projects.core.model)
    api(projects.core.util)

    implementation(projects.core.common)

//    testImplementation(projects.core.datastoreTest)
    testImplementation(libs.kotlinx.coroutines.test)
}
