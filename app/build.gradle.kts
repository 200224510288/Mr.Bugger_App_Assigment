plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.mrbugger_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mrbugger_app"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    //AndroidX
    implementation(libs.bundles.androidx)
    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.engage.core)
    implementation(libs.generativeai)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.litert.support.api)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    debugImplementation(libs.compose.tooling)
    implementation(libs.bundles.ui)
    implementation("androidx.compose.material:material:1.4.0")
    implementation ("androidx.compose.material:material-icons-extended:1.5.0")
    implementation ("com.google.maps.android:maps-compose:2.7.2")

}