plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.secrets)
    alias(libs.plugins.room)
    alias(libs.plugins.gms)
}

android {
    namespace = "com.oreocube.booksearch.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
    sourceSets {
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging)
    implementation(libs.androidx.paging.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.worker)
    implementation(libs.androidx.hilt.work)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.room.testing.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
