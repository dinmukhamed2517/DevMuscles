plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //google service plugin
    id("com.google.gms.google-services")

    //save args
    id("androidx.navigation.safeargs")

    // parselize
    id("kotlin-parcelize")


    id ("kotlin-kapt")

    //hilt
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "kz.just_code.devmuscles"
    compileSdk = 34

    defaultConfig {
        applicationId = "kz.just_code.devmuscles"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    //Glide
    implementation ("com.github.bumptech.glide:glide:4.13.0")
    kapt("com.github.bumptech.glide:compiler:4.13.0")

    // hilt DI
    implementation("com.google.dagger:hilt-android:2.46")
    kapt ("com.google.dagger:hilt-compiler:2.46")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // fragment extension
    implementation ("androidx.fragment:fragment-ktx:1.2.5")

    //fragment navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")

    //material design
    implementation ("com.google.android.material:material:1.10.0")

    // firebase bom
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics")

    // firebase auth
    implementation("com.google.firebase:firebase-auth")

    //lottie
    implementation ("com.airbnb.android:lottie:5.2.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:1.8.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}