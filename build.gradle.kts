import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false

    id("com.google.dagger.hilt.android") version "2.57" apply false
    id("io.gitlab.arturbosch.detekt") version("1.23.8")
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config.from(rootProject.files("config/detekt/detekt.yml"))
    }
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
    }
}
