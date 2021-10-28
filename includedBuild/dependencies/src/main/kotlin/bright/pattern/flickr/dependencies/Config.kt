package bright.pattern.flickr.dependencies

object Android {
    const val applicationId = "bright.pattern.flickr"
    const val appVersionCode = 1
    const val appVersionName = "1.0"
    const val minSdkVersion = 21
    const val targetSdkVersion = 31
    const val compileSdkVersion = 31
}

object Libs {

    object AndroidX {
        private const val appcompatVersion = "1.3.1"
        private const val coreKtxVersion = "1.6.0"
        private const val fragmentKtxVersion = "1.3.6"
        private const val navigationFragmentVersion = "2.3.5"

        const val appcompat = "androidx.appcompat:appcompat:$appcompatVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:$fragmentKtxVersion"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationFragmentVersion"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:$navigationFragmentVersion"
    }

    object Kotlin {
        private const val coroutinesVersion = "1.5.2"

        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object MVVM {
        object AndroidX {
            private const val lifecycleVersion = "2.3.1"
            private const val viewBindingVersion = "7.0.3"

            const val livedataKtx =
                "androidx.lifecycle:lifecycle-livedata-ktx:${AndroidX.lifecycleVersion}"
            const val viewmodelKtx =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${AndroidX.lifecycleVersion}"
            const val lifecycleCommonJava8 =
                "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
            const val viewBinding =
                "androidx.databinding:viewbinding:$viewBindingVersion"
        }
    }

    object UI {
        private const val androidMaterialVersion = "1.4.0"
        private const val glideVersion = "4.11.0"

        const val androidMaterial = "com.google.android.material:material:$androidMaterialVersion"
        const val glide = "com.github.bumptech.glide:glide:$glideVersion"


        object AndroidX {
            private const val constraintLayoutVersion = "2.1.1"
            private const val swiperefreshlayoutVersion = "1.1.0"

            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
            const val swiperefreshlayout =
                "androidx.swiperefreshlayout:swiperefreshlayout:$swiperefreshlayoutVersion"
        }
    }

    object Logging {
        private const val timberVersion = "4.7.1"

        const val timber = "com.jakewharton.timber:timber:$timberVersion"
    }

    object Network {
        private const val okhttpVersion = "4.9.2"
        private const val retrofit2Version = "2.9.0"
        private const val retrofit2KotlinCoroutinesAdapterVersion = "0.9.2"

        const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        const val okhttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofit2Version"
        const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:$retrofit2Version"
        const val retrofit2KotlinCoroutinesAdapter =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofit2KotlinCoroutinesAdapterVersion"
    }
}

object TestLibs {
    const val junit = "junit:junit:4.+"
    const val androidxJunit = "androidx.test.ext:junit:1.1.3"
    const val androidxEspresso = "androidx.test.espresso:espresso-core:3.4.0"
}