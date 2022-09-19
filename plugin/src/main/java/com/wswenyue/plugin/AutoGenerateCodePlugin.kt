package com.wswenyue.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoGenerateCodePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("===${target.name}====")
    }
}