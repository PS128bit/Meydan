// AGP 9 compiles Kotlin itself (built-in Kotlin), so no separate Kotlin plugin is applied.
// The Kotlin JVM target follows compileOptions.targetCompatibility below.
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.supernova.meydan"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.supernova.meydan"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1-ui"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewpager2)
}
