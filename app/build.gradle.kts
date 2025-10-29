plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.plugin)
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.flux.store"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.flux.store"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    signingConfigs {
        create("release") {
            keyAlias = "yourKeyAlias"
            keyPassword = "yourKeyPassword"
            storeFile = file("path/to/your/keystore.jks")
            storePassword = "yourStorePassword"
        }
    }

    /**
     * Build types — only debug vs release matter for URLs.
     * All flavors share the same logic.
     */
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true

            buildConfigField("String", "BASE_URL", "\"https://dev-api.togopool.com/\"")
            buildConfigField("String", "CHAT_BASE_URL", "\"https://dev.mobilityinfotech.com:8456/\"")
            buildConfigField("String", "OLA_API_BASE_URL", "\"https://api.olamaps.io\"")
            buildConfigField("String", "GOOGLE_API_BASE_URL", "\"https://maps.googleapis.com/\"")
        }

        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false

            buildConfigField("String", "BASE_URL", "\"https://api.togopool.com/\"")
            buildConfigField("String", "CHAT_BASE_URL", "\"https://chat-api.togopool.com/\"")
            buildConfigField("String", "OLA_API_BASE_URL", "\"https://api.olamaps.io\"")
            buildConfigField("String", "GOOGLE_API_BASE_URL", "\"https://maps.googleapis.com/\"")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    /**
     * Product Flavors — same code, different branding.
     * Does NOT affect which URLs are used.
     */
    flavorDimensions += "version"

    productFlavors {
        create("flux") {
            dimension = "version"
            applicationId = "com.flux.store"
            resValue("string", "app_name", "Flux Store")
            buildConfigField("String", "APP_FLAVOR", "\"flux\"")
        }

        create("lient") {
            dimension = "version"
//            applicationId = "com.flux.store"
            applicationId = "com.flux.client"
            resValue("string", "app_name", "Client Store")
            buildConfigField("String", "APP_FLAVOR", "\"client\"")
        }

        create("demo") {
            dimension = "version"
//            applicationId = "com.flux.store"
            applicationId = "com.demo.flux"
            resValue("string", "app_name", "Demo Store")
            buildConfigField("String", "APP_FLAVOR", "\"demo\"")
            buildConfigField("long", "DEMO_EXPIRY_DAYS", "7L")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions { jvmTarget = "17" }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.0"
    }

    kapt {
        correctErrorTypes = true
        showProcessorStats = true
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    // --- Jetpack Compose BOM ---
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))  // ADD THIS
    debugImplementation(platform(libs.androidx.compose.bom))       // ADD THIS (optional but safe)

    // Compose core
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.foundation)
    implementation(libs.material)
    implementation(libs.androidx.compose.material3)

    // Material Icons, adaptive layouts
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3.window.size.class1)
    implementation(libs.androidx.material3.adaptive.navigation.suite)

    // Lifecycle + Navigation + Hilt
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // KotlinX
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.gson)

    // Firebase + DataStore + Coil
    implementation(libs.firebase.config)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.coil.compose)

    // Accompanist (system UI control)
    implementation(libs.accompanist.systemuicontroller)

    // --- Testing + Debug ---
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
}
