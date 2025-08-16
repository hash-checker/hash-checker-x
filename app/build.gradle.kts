import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "xyz.fartem.hashcheckerx"
    compileSdk = 36

    defaultConfig {
        applicationId = "xyz.fartem.hashcheckerx"
        minSdk = 24
        targetSdk = 36
        versionCode = 5
        versionName = "1.3.0"

        buildConfigField(
            "String",
            "URL_AUTHOR",
            "\"https://github.com/fartem\""
        )
        buildConfigField(
            "String",
            "URL_SOURCE_CODE",
            "\"https://github.com/hash-checker/hash-checker-x\""
        )
        buildConfigField(
            "String",
            "URL_RELEASES",
            "\"https://github.com/hash-checker/hash-checker-x/releases/tag/\""
        )
        buildConfigField(
            "String",
            "URL_PRIVACY_POLICY",
            "\"https://hash-checker.github.io/hash-checker-x-privacy-policy.io/\""
        )

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    androidComponents {
        onVariants { variant ->
            variant.outputs.forEach { output ->
                if (output is com.android.build.api.variant.impl.VariantOutputImpl) {
                    output.outputFileName = "hash-checker-x-${output.versionName.get()}.apk"
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":feedback"))
    implementation(project(":hash-generator"))
    implementation(project(":history"))
    implementation(project(":settings"))

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}
