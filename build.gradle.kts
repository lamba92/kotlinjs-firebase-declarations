allprojects {
    group = "com.github.lamba92"
    version = System.getenv("GITHUB_REF")?.split("/")?.last() ?: "0.1.8"
    repositories {
        jcenter()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin-js-firebase-declarations-plugin")
}

task("generateReadme") {
    group = "documentation"
    inputs.file("README-TEMPLATE.md")
    val packages = file("packages").listFiles { f -> f.isDirectory }!!
        .filterIsInstance<File>()
        .onEach {
            inputs.dir(it.absolutePath)
        }
    outputs.file("README.md")
    doLast {
        with(file("README.md")) {
            delete()
            createNewFile()
            writeText(file("README-TEMPLATE.md")
                .readText()
                .replace("%%%REPLACE-DEFINITIONS%%%", buildString {
                    packages.forEach {
                        appendln("    implementation(\"com.github.lamba92\", \"${rootProject.name}-${it.name}\", kotlinJsFirebaseDeclarationsVersion)")
                    }
                })
                .replace("%%%REPLACE-DEFINITIONS-2%%%", buildString {
                    packages.forEach {
                        appendln("            implementation(\"com.github.lamba92:${rootProject.name}-${it.name}:\$kotlinJsFirebaseDeclarationsVersion\")")
                    }
                })
            )
        }
    }
}
