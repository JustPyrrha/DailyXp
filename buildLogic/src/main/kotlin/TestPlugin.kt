import com.android.build.api.dsl.TestExtension
import gay.pyrrha.build.configureGradleManagedDevices
import gay.pyrrha.build.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class TestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                configureGradleManagedDevices(this)
            }
        }
    }
}
