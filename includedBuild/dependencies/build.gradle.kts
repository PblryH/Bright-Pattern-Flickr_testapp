plugins {
    `kotlin-dsl`
}

// To make it available as direct dependency
group = "bright.pattern.flickr.dependencies"
version = "SNAPSHOT"

repositories {
    jcenter()
}

gradlePlugin {
    plugins.register("dependencies") {
        id = "dependencies"
        implementationClass = "bright.pattern.flickr.dependencies.DependenciesPlugin"
    }
}
