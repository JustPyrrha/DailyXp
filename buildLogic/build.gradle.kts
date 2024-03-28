import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    `kotlin-dsl`
}

group = "gay.pyrrha.build"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    implementation(libs.truth)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        val applicationCompose by creating {
            id = "xp.android.application.compose"
            implementationClass = "ApplicationComposePlugin"
        }

        val applicationFlavours by creating {
            id = "xp.android.application.flavours"
            implementationClass = "ApplicationFlavoursPlugin"
        }

        val applicationJacoco by creating {
            id = "xp.android.application.jacoco"
            implementationClass = "ApplicationJacocoPlugin"
        }

        val application by creating {
            id = "xp.android.application"
            implementationClass = "ApplicationPlugin"
        }

        val feature by creating {
            id = "xp.android.feature"
            implementationClass = "FeaturePlugin"
        }

        val hilt by creating {
            id = "xp.android.hilt"
            implementationClass = "HiltPlugin"
        }

        val libraryCompose by creating {
            id = "xp.android.library.compose"
            implementationClass = "LibraryComposePlugin"
        }

        val libraryJacoco by creating {
            id = "xp.android.library.jacoco"
            implementationClass = "LibraryJacocoPlugin"
        }

        val library by creating {
            id = "xp.android.library"
            implementationClass = "LibraryPlugin"
        }

        val lint by creating {
            id = "xp.android.lint"
            implementationClass = "LintPlugin"
        }

        val multiplatformLibrary by creating {
            id = "xp.android.library.multiplatform"
            implementationClass = "MultiplatformLibraryPlugin"
        }

        val test by creating {
            id = "xp.android.test"
            implementationClass = "TestPlugin"
        }

        val jvmLibrary by creating {
            id = "xp.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }
    }
}