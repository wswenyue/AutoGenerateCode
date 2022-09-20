package com.wswenyue.plugin

import com.android.build.gradle.tasks.GenerateBuildConfig
import com.wswenyue.plugin.tasks.GenerateCodeTask
import com.wswenyue.plugin.utils.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile

class AutoGenerateCodePlugin : Plugin<Project> {
    companion object {
        const val TAG = "AutoGenerateCodePlugin"
    }

    override fun apply(target: Project) {
        if (target.isApplication()) {
            //Application
            println("====Apply====${TAG}=====Application:${target.name}=============".green())

            target.tasks.whenTaskAdded { theTask: Task ->
                if (theTask is GenerateBuildConfig) {
                    println("GenerateBuildConfig==>${target.name}#${theTask.name}")
                    val generateCodeTask = target.buildTask<GenerateCodeTask>(
                        name = "GenerateCode", group = "easy"
                    )
                    theTask.dependsOn(generateCodeTask)
                }
            }
        } else if (target.isLibrary() || target.isDynamicFeature()) {
            //Library
            println("====Apply====${TAG}=====Library(or DynamicFeature):${target.name}=============".green())

            target.tasks.whenTaskAdded { theTask: Task ->
                if (theTask is JavaCompile && theTask.name == "compileJava") {
                    println("JavaCompile==>${target.name}#${theTask.name}")
                    val generateCodeTask = target.buildTask<GenerateCodeTask>(
                        name = "GenerateCode", group = "easy"
                    )
                    theTask.dependsOn(generateCodeTask)
                }
            }
        } else {
            println("unknown project type!!!".red())
        }
    }
}