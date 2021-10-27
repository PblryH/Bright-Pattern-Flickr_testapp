import bright.pattern.flickr.dependencies.Android
import bright.pattern.flickr.dependencies.Libs
import bright.pattern.flickr.dependencies.TestLibs

plugins {
    id("dependencies")
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Android.compileSdkVersion

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.Kotlin.coroutinesCore)

    testImplementation (TestLibs.junit)
    androidTestImplementation (TestLibs.androidxJunit)
    androidTestImplementation (TestLibs.androidxEspresso)
}