plugins {
    alias(libs.plugins.booksearch.android.feature)
}

android {
    namespace = "com.oreocube.booksearch.feature.book"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)
    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.mlkit.vision)
    implementation (libs.barcode.scanning)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
