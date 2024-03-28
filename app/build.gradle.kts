import gay.pyrrha.build.XpBuildType

plugins {
    alias(libs.plugins.aboutLicenses)
    alias(libs.plugins.xp.android.application)
    alias(libs.plugins.xp.android.application.compose)
    alias(libs.plugins.xp.android.application.flavours)
    alias(libs.plugins.xp.android.application.jacoco)
    alias(libs.plugins.xp.android.hilt)
}

android {
    namespace = "gay.pyrrha.dailyxp"
    defaultConfig {
        applicationId = "gay.pyrrha.dailyxp"
        versionCode = 1
        versionName = "0.1.0"

        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = XpBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = XpBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.feature.more)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.quests)

    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.design)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.i18n)

    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.material3Adaptive)
    implementation(libs.androidx.compose.material3AdaptiveLayout)
    implementation(libs.androidx.compose.material3AdaptiveNavigation)
    implementation(libs.androidx.compose.material3WSC)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.coreSplashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}
