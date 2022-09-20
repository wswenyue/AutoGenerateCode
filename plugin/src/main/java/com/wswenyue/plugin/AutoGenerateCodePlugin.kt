package com.wswenyue.plugin

import com.wswenyue.plugin.tasks.GenerateCodeTask
import com.wswenyue.plugin.utils.CommUtils
import com.wswenyue.plugin.utils.buildTask
import com.wswenyue.plugin.utils.getEnvMapWarp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile

class AutoGenerateCodePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("=============${target.name}===begin=============")
        target.tasks.whenTaskAdded { theTask: Task ->
            if (theTask is JavaCompile) {
                if (theTask.name == "compileJava" && isNeedGenerate(target)) {
                    println("JavaCompile==>${target.name}#${theTask.name}")
                    val generateCodeTask = target.buildTask<GenerateCodeTask>(
                        name = "GenerateCode",
                        group = "easy"
                    )

                    theTask.dependsOn(generateCodeTask)
                }
            }
        }
        println("=============${target.name}===end===============")
    }

    private fun isNeedGenerate(target: Project): Boolean {
        target.getEnvMapWarp(AGCConstant.keyAgcIndex)?.let { cfg ->
            val outDir = cfg[AGCConstant.keyAgcOutDir] as String?
            val packageName = cfg[AGCConstant.keyAgcPackage] as String?
            val clsName = cfg[AGCConstant.keyAgcClsName] as String?
            if (CommUtils.isNotEmpty(outDir)
                && CommUtils.isNotEmpty(packageName)
                && CommUtils.isNotEmpty(clsName)
            ) {
                return true
            }
        }
        return false
    }
}