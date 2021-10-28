import bright.pattern.flickr.dependencies.Android
import bright.pattern.flickr.dependencies.Libs
import bright.pattern.flickr.dependencies.TestLibs

plugins {
    id("dependencies")
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = Android.compileSdkVersion

    defaultConfig {
        applicationId = Android.applicationId
        minSdk = Android.minSdkVersion
        targetSdk = Android.targetSdkVersion
        versionCode = Android.appVersionCode
        versionName = Android.appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.fragmentKtx)
    implementation(Libs.AndroidX.navigationFragment)
    implementation(Libs.AndroidX.navigationUI)
    implementation(Libs.Kotlin.coroutinesCore)
    implementation(Libs.Kotlin.coroutinesAndroid)
    implementation(Libs.MVVM.AndroidX.livedataKtx)
    implementation(Libs.MVVM.AndroidX.viewmodelKtx)
    implementation(Libs.MVVM.AndroidX.lifecycleCommonJava8)
    implementation(Libs.MVVM.AndroidX.viewBinding)
    implementation(Libs.Logging.timber)
    implementation(Libs.Network.okhttp)
    implementation(Libs.Network.okhttpLoggingInterceptor)
    implementation(Libs.Network.retrofit2)
    implementation(Libs.Network.retrofit2ConverterGson)
    implementation(Libs.Network.retrofit2KotlinCoroutinesAdapter)
    implementation(Libs.UI.androidMaterial)
    implementation(Libs.UI.glide) { exclude(group = "com.android.support") }
    implementation(Libs.UI.AndroidX.constraintLayout)
    implementation(Libs.UI.AndroidX.swiperefreshlayout)


    testImplementation (TestLibs.junit)
    androidTestImplementation (TestLibs.androidxJunit)
    androidTestImplementation (TestLibs.androidxEspresso)
}