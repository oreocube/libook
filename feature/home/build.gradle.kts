plugins {
    id("booksearch.android.feature")
}

android {
    namespace = "com.oreocube.booksearch.feature.home"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
