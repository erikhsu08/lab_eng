plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.reciclamais"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.reciclamais"
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
    testOptions{
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.ext.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Dependência do Firebase Realtime Database
    implementation (libs.firebase.database)
    testImplementation (libs.firebase.database.v2005)

    // Dependências para testes instrumentados com Espresso
    androidTestImplementation(libs.espresso.core.v340)
    androidTestImplementation (libs.junit.v113)

    // Outras dependências de testes
    testImplementation (libs.junit)


    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.firebase.analytics)


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

}