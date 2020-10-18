plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    google()
    mavenCentral()
    maven("https://dl.bintray.com/lamba92/com.github.lamba92")
}

gradlePlugin.plugins.create("kotlin-js-firebase-declarations-plugin") {
    id = "kotlin-js-firebase-declarations-plugin"
    implementationClass = "KotlinJsFirebaseDeclarationsGradlePlugin"
}

dependencies {
    api(kotlin("gradle-plugin", "1.4.10"))
    api(kotlin("reflect", "1.4.10"))
    api("com.github.lamba92", "lamba-gradle-utils", "1.1.0")
}
