import com.android.build.api.dsl.ApplicationExtension
import gay.pyrrha.build.configureFlavours
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationFlavoursPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            extensions.configure<ApplicationExtension> {
                configureFlavours(this)
            }
        }
    }
}
