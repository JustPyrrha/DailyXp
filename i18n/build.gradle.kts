import gay.pyrrha.build.registerLocalesConfigTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.xp.android.library.multiplatform)
    alias(libs.plugins.moko)
}

kotlin {
    androidTarget()
    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.moko.core)
            }
        }

        androidMain {
            dependsOn(commonMain)
        }
    }
}

android {
    namespace = "gay.pyrrha.dailyxp.i18n"
    compileSdk = 34

    sourceSets {
        named("main") {
            res.srcDir("src/commonMain/resources")
        }
    }

    lint {
        disable.addAll(listOf("MissingTranslation", "ExtraTranslation"))
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "gay.pyrrha.dailyxp.i18n"
}

tasks {
    val localesConfigTask = registerLocalesConfigTask(project)
    preBuild {
        dependsOn(localesConfigTask)
    }

    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += listOf(
            "-Xexpect-actual-classes"
        )
    }
}
