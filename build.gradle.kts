buildscript {
    dependencies {
        classpath(libs.moko.gradle)
    }
}

plugins {
    alias(libs.plugins.aboutLicenses) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.grgit) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.moko) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.roborazzi) apply false

    // from buildLogic
    alias(libs.plugins.xp.android.application) apply false
    alias(libs.plugins.xp.android.application.compose) apply false
    alias(libs.plugins.xp.android.application.flavours) apply false
    alias(libs.plugins.xp.android.application.jacoco) apply false
    alias(libs.plugins.xp.android.feature) apply false
    alias(libs.plugins.xp.android.hilt) apply false
    alias(libs.plugins.xp.android.library) apply false
    alias(libs.plugins.xp.android.library.compose) apply false
    alias(libs.plugins.xp.android.library.jacoco) apply false
    alias(libs.plugins.xp.android.library.multiplatform) apply false
    alias(libs.plugins.xp.android.lint) apply false
    alias(libs.plugins.xp.android.test) apply false
    alias(libs.plugins.xp.jvm.library) apply false
}
