import com.oreocube.booksearch.extension.configureHiltAndroid
import com.oreocube.booksearch.extension.configureSerializationAndroid
import com.oreocube.booksearch.extension.libs

plugins {
    id("booksearch.android.library")
    id("booksearch.android.compose")
}

configureHiltAndroid()
configureSerializationAndroid()

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":domain"))

    implementation(libs.findLibrary("androidx.hilt.navigation.compose").get())
    implementation(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
    implementation(libs.findLibrary("androidx.navigation.compose").get())
    implementation(libs.findLibrary("kotlinx.serialization.json").get())
}
