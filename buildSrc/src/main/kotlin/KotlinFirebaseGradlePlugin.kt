import com.jfrog.bintray.gradle.BintrayPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import com.github.lamba92.gradle.utils.prepareForPublication
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*

@Suppress("unused")
open class KotlinJsFirebaseDeclarationsGradlePlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {

        apply<KotlinJsPluginWrapper>()
        apply<MavenPublishPlugin>()
        apply<BintrayPlugin>()

        kotlin {
            js(BOTH) {
                nodejs()
            }
            sourceSets {
                val main by getting {
                    dependencies {
                        if (project.name == "core") {
                            val firebaseJsVersion: String by project
                            api(npm("firebase", firebaseJsVersion))
                        } else
                            api(project(":core"))
                    }
                }
                tasks.create<Jar>("sourcesJar") {
                    archiveClassifier.set("sources")
                    from(main.kotlin.sourceDirectories)
                }
            }
        }

        publishing.publications {
            create<MavenPublication>(project.name) {
                from(components["kotlin"])
                artifact(tasks.named<Jar>("sourcesJar"))
                groupId = project.group as String
                artifactId = "${rootProject.name}-${project.name}"
                version = project.version as String
            }
        }

        prepareForPublication()

    }

}

private fun Project.kotlin(action: KotlinJsProjectExtension.() -> Unit) =
    kotlin.apply(action)

private val Project.kotlin
    get() = extensions.getByType<KotlinJsProjectExtension>()

private fun Project.publishing(action: PublishingExtension.() -> Unit) =
    publishing.apply(action)

private val Project.publishing
    get() = extensions.getByType<PublishingExtension>()
