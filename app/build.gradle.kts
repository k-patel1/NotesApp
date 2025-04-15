plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.finalassignmentnotesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.finalassignmentnotesapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Volley for network requests (affirmations API)
    implementation("com.android.volley:volley:1.2.1")

    // Room components for database (if you decide to add it later)
    // implementation("androidx.room:room-runtime:2.5.2")
    // annotationProcessor("androidx.room:room-compiler:2.5.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}