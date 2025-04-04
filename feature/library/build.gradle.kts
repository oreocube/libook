plugins {
    id("booksearch.android.feature")
}

android {
    namespace = "com.oreocube.booksearch.feature.library"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
