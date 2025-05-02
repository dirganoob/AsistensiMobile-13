plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.instagram03"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.instagram03"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Circle ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")


}
