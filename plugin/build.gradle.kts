plugins {
    id("java-gradle-plugin")
    id("kotlin")
    id("com.gradle.plugin-publish") version "1.0.0"
    id("maven-publish")
//    signing
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.0")
    implementation("com.squareup:javapoet:1.13.0")
}

val pluginGroup = "vip.wswenyue"
val pluginName = "AutoGenerateCodePlugin"
val pluginGroupId = "vip.wswenyue.AutoGenerateCode"
val pluginArtifactId = "${pluginGroupId}.gradle.plugin"
val pluginVersion = "0.1.0"
val pluginDescription = "A gradle plugin that generates code automatically."
val pluginWebSite = "https://wswenyue.github.io/AutoGenerateCode"
val pluginVcsUrl = "https://github.com/wswenyue/AutoGenerateCode"

group = "vip.wswenyue.plugins"
version = pluginVersion
description = pluginDescription

// Use java-gradle-plugin to generate plugin descriptors and specify plugin ids
gradlePlugin {
    plugins {
        create(pluginName) {
            id = pluginGroupId
            displayName = pluginName
            description = pluginDescription
            implementationClass = "com.wswenyue.plugin.AutoGenerateCodePlugin"
        }
    }
}

//https://plugins.gradle.org/plugin/com.gradle.plugin-publish
//https://plugins.gradle.org/docs/publish-plugin
pluginBundle {
    website = pluginWebSite
    vcsUrl = pluginVcsUrl
    tags = listOf("auto", "generate", "code", "android")
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = pluginGroupId
            artifactId = pluginArtifactId
            version = pluginVersion
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(pluginName)
                description.set(pluginDescription)
                url.set(pluginVcsUrl)
//                properties.set(
//                    mapOf(
//                        "myProp" to "value", "prop.with.dots" to "anotherValue"
//                    )
//                )
                licenses {
                    license {
                        name.set("The BSD 2-Clause \"Simplified\" License")
                        url.set("https://github.com/wswenyue/AutoGenerateCode/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("wswenyue")
                        name.set("wswenyue")
                        email.set("wswenyue@163.com")
                    }
                }
                scm {
                    //scm:git:git://github.com/path_to_repository
                    connection.set("scm:git:git://github.com/wswenyue/AutoGenerateCode")
                    //scm:git:ssh://github.com/path_to_repository
                    developerConnection.set("scm:git:ssh://github.com/wswenyue/AutoGenerateCode")
                    url.set(pluginVcsUrl)
                }
            }
        }
    }
//    repositories {
//        maven {
//            // change URLs to point to your repos, e.g. http://my.org/repo
//            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
//            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
//            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
//        }
//    }
}

//signing {
//    sign(publishing.publications["mavenJava"])
//}