package com.oreocube.booksearch.extension

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureSerializationAndroid() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.serialization")
    }

    dependencies {
        "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
    }
}
