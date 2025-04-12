plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.assignment.reportviewerapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.assignment.reportviewerapp"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core libraries
    implementation(libs.androidx.core.ktx)  // Core Kotlin extensions
    implementation(libs.androidx.lifecycle.runtime.ktx)  // Lifecycle extensions
    implementation(libs.androidx.activity.compose)  // Activity Compose integration

    // Compose libraries
    implementation(platform(libs.androidx.compose.bom))  // BOM for Compose version management
    implementation(libs.androidx.ui)  // Compose UI components
    implementation(libs.androidx.ui.graphics)  // Compose graphics utilities
    implementation(libs.androidx.ui.tooling.preview)  // Compose UI tooling preview
    implementation(libs.androidx.material3)  // Material3 components
    implementation(libs.androidx.material.icons.extended)

    // Retrofit for API calls
    implementation(libs.retrofit)  // Retrofit for network requests
    implementation(libs.retrofit.gson)  // Gson converter for Retrofit

    // Room DB for local storage
    implementation(libs.room.runtime)
    implementation(libs.androidx.navigation.compose)  // Room runtime for database access
    annotationProcessor(libs.room.compiler)  // Room compiler for annotation processing

    // Firebase libraries
    implementation(libs.firebase.auth)  // Firebase Authentication
    implementation(libs.firebase.messaging)  // Firebase Cloud Messaging (FCM)

    // Koin for Dependency Injection
    implementation(libs.koin.android)  // Koin for Android DI

    // Image loading
    implementation(libs.coil.compose)  // Coil for image loading in Compose

    // Testing dependencies
    testImplementation(libs.junit)  // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit)  // AndroidJUnit for UI testing
    androidTestImplementation(libs.androidx.espresso.core)  // Espresso for UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom))  // BOM for Compose testing
    androidTestImplementation(libs.androidx.ui.test.junit4)  // Compose testing JUnit support
    debugImplementation(libs.androidx.ui.tooling)  // Compose UI tooling
    debugImplementation(libs.androidx.ui.test.manifest)  // Manifest for Compose testing
}