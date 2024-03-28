import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import gay.pyrrha.build.configureFlavours
import gay.pyrrha.build.configureGradleManagedDevices
import gay.pyrrha.build.configureKotlinAndroid
import gay.pyrrha.build.configurePrintApksTask
import gay.pyrrha.build.disableUnnecessaryAndroidTests
import gay.pyrrha.build.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin


class MultiplatformLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("xp.android.lint")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                testOptions {
                    targetSdk = 34
                    animationsDisabled = true
                }
                configureFlavours(this)
                configureGradleManagedDevices(this)
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix = path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_").lowercase() + "_"
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(project)
            }

            dependencies {
                add("testImplementation", kotlin("test"))
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}
