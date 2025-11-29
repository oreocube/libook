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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
