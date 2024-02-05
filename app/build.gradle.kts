plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.crystal2033.qrextractor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.crystal2033.qrextractor"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        allWarningsAsErrors = false
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            pickFirsts += listOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/ASL-2.0.txt",
                "META-INF/LICENSE.md",
                "META-INF/NOTICE.md",
                "META-INF/LGPL-3.0.txt",
            )
            excludes += listOf(
                "META-INF/kotlin-jupyter-libraries/libraries.json",
                "META-INF/{INDEX.LIST,DEPENDENCIES}",
                "{draftv3,draftv4}/schema",
                "arrow-git.properties",
            )
        }

    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}




dependencies {
    implementation("androidx.documentfile:documentfile:1.0.1")
//
//    implementation("org.jetbrains.kotlinx:dataframe:0.12.0"){
//        exclude(group = "commons-logging", module = "commons-logging")
//    }
//    implementation("org.slf4j:jcl-over-slf4j:2.0.9")

    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")

//    runtimeOnly("org.apache.logging.log4j:log4j-api-kotlin:1.4.0")
//    implementation("org.apache.logging.log4j:log4j-core:2.20.0")


    val hilt_version = "2.48.1"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    ksp("com.google.dagger:hilt-android-compiler:$hilt_version")
    //ksp("androidx.hilt:hilt-compiler:1.1.0")

    // https://mvnrepository.com/artifact/androidx.hilt/hilt-navigation-compose
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    val nav_version = "2.7.5"
    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    //For viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    //Retrofit
    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation ("com.squareup.retrofit2:converter-gson:${retrofitVersion}")

    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // https://mvnrepository.com/artifact/androidx.compose.material/material-icons-extended
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")

    // CameraX core library using the camera2 implementation
    val camerax_version = "1.4.0-alpha03"
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    // If you want to additionally use the CameraX Lifecycle library
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    // If you want to additionally use the CameraX View class
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("androidx.camera:camera-extensions:${camerax_version}")
    //QR-code
    implementation("com.google.zxing:core:3.4.0")

    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}