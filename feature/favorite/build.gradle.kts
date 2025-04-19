plugins {
    alias(libs.plugins.booksearch.android.feature)
}

android {
    namespace = "com.oreocube.booksearch.feature.favorite"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
