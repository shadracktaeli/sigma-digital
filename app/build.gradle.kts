plugins {
    id("dagger.hilt.android.plugin")
    id("com.android.application")
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "a.co.codevue.sigmadigital"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.time.ExperimentalTime"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }

    sourceSets {
        val main by getting
        main.res.srcDir(listOf("src/main/res", file("src/main/layouts").listFiles()))

        val test by getting
        test.java.srcDir("src/testShared")
        val androidTest by getting
        androidTest.java.srcDir("src/testShared")
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Shared module
    api(project(":shared"))
    // App libs
    implementation(AppDependencies.appModuleLibraries)
    // Kapt
    kapt(AppDependencies.kaptAppLibraries)
    // Test libs
    testImplementation(AppDependencies.testAppLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)
}