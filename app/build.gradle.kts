plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.harmony_chat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.harmony_chat"
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
        sourceCompatibility =JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.makeramen:roundedimageview:2.3.0")

//    implementation ("com.github.bumptech.glide:glide:4.12.0")
//    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

//    implement loading library
    implementation ("com.squareup.picasso:picasso:2.71828")
//    thư viện thêm - 21130463
    implementation("androidx.cardview:cardview:1.0.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Convert gson
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // Gson
    implementation("com.google.code.gson:gson:2.8.7")
    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.0.13")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

//    dang nhap bang google
    implementation ("com.google.android.gms:play-services-auth:20.4.0")

//    mail
    implementation(files("libs/mail.jar"))
    implementation(files("libs/additionnal.jar"))
    implementation(files("libs/activation.jar"))


}