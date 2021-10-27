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
    implementation(Libs.UI.AndroidX.constraintLayout)

    implementation("com.github.bumptech.glide:glide:4.11.0") {
        exclude(group = "com.android.support")
    }
    implementation("com.android.support:support-fragment:26.1.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")


    testImplementation (TestLibs.junit)
    androidTestImplementation (TestLibs.androidxJunit)
    androidTestImplementation (TestLibs.androidxEspresso)
}