package com.oreocube.booksearch.extension

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureComposeAndroid() {
    with(plugins) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }
    extensions.getByType<BaseExtension>().apply {
        buildFeatures.apply {
            compose = true
        }
    }

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        "implementation"(platform(bom))
        "implementation"(libs.findLibrary("androidx.ui").get())
        "implementation"(libs.findLibrary("androidx.ui.tooling").get())
        "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
        "debugImplementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
    }
}
