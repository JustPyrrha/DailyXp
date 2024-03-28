import com.android.build.api.variant.LibraryAndroidComponentsExtension
import gay.pyrrha.build.configureJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class LibraryJacocoPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("org.gradle.jacoco")
                apply("com.android.library")
            }

            val extension = extensions.getByType<LibraryAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }
}
