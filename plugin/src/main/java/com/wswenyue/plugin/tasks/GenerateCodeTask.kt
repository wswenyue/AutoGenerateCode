package com.wswenyue.plugin.tasks

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.wswenyue.plugin.AGCConstant
import com.wswenyue.plugin.utils.CommUtils
import com.wswenyue.plugin.utils.getEnvMapWarp
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.lang.model.element.Modifier

open class GenerateCodeTask : DefaultTask() {
    @TaskAction
    fun doRun() {
        println("======GenerateCodeTask===begin==")
        println("name:${project.name}")

        project.getEnvMapWarp(AGCConstant.keyAgcIndex)?.let { cfg ->
            val outDir = cfg[AGCConstant.keyAgcOutDir] as String?
            val packageName = cfg[AGCConstant.keyAgcPackage] as String?
            val clsName = cfg[AGCConstant.keyAgcClsName] as String?
            if (CommUtils.isNotEmpty(outDir)
                && CommUtils.isNotEmpty(packageName)
                && CommUtils.isNotEmpty(clsName)
            ) {

                val builder = TypeSpec.classBuilder(clsName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)

                project.getEnvMapWarp(AGCConstant.keyFieldsIndex)
                    ?.forEach { (fKey, fValue) ->
                        builder.addField(
                            FieldSpec.builder(String::class.java, fKey)
                                .addModifiers(Modifier.STATIC, Modifier.PUBLIC, Modifier.FINAL)
                                .initializer("\$S", fValue.toString())
                                .build()
                        )
                    }

                File(project.projectDir, outDir!!).run {
                    mkdirs()
                    JavaFile.builder(packageName, builder.build())
                        .build().writeTo(this)
                }

            }
        }

        println("======GenerateCodeTask===end==")
    }
}