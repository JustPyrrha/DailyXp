import gay.pyrrha.build.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("xp.android.lint")
            }
            configureKotlinJvm()
        }
    }
}
