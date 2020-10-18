rootProject.name = "kotlinjs-firebase-declarations"

val packages: Array<File> = file("packages").listFiles { f: File -> f.isDirectory } ?: emptyArray()

include(*packages.map { it.name }.toTypedArray())

packages.forEach {
    project(":${it.name}").projectDir = it
}
