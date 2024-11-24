plugins {
    id("com.android.application") // Android 앱 플러그인
    id("com.google.gms.google-services") // Firebase 플러그인
}

android {
    namespace = "com.example.loms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.loms"
        minSdk = 26
        targetSdk = 34
        versionCode = 20
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.activity:activity:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-firestore-ktx") // Firestore 라이브러리
    implementation("com.google.firebase:firebase-analytics-ktx") // Firebase Analytics 라이브러리
    implementation("com.google.firebase:firebase-auth-ktx") // Firebase Authentication 라이브러리
    implementation("com.google.firebase:firebase-database-ktx")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}
