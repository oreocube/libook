package com.oreocube.booksearch.extension

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureKotlinAndroid() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.android")
    }

    extensions.getByType<BaseExtension>().apply {
        compileSdkVersion(35)

        defaultConfig {
            minSdk = 26
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(11)
    }
}
