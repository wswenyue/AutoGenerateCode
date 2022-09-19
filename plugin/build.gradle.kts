plugins {
    id("java-gradle-plugin")
    id("kotlin")
    id("com.gradle.plugin-publish") version "1.0.0"
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


val artifactId = "AutoGenerateCodePlugin"

// Use java-gradle-plugin to generate plugin descriptors and specify plugin ids
gradlePlugin {
    plugins {
        create(artifactId) {
            id = "com.wswenyue.autogeneratecode"
            displayName = "AutoGenerateCodePlugin"
            description = "A gradle plugin that generates code automatically."
            implementationClass = "com.wswenyue.plugin.AutoGenerateCodePlugin"
        }
    }
}

//https://plugins.gradle.org/plugin/com.gradle.plugin-publish
//https://plugins.gradle.org/docs/publish-plugin
pluginBundle {
    website = "https://github.com/wswenyue/AutoGenerateCode"
    vcsUrl = "https://github.com/wswenyue/AutoGenerateCode"
    tags = listOf("auto", "generate", "code", "android")
}