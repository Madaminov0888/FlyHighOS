import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    kotlin("plugin.serialization") version "1.9.10"
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation("com.composables:core:1.20.0")

            implementation("network.chaintech:kmp-date-time-picker:1.0.5")

            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")

            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1") // Or the latest version

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

            implementation("io.ktor:ktor-client-core:2.3.4")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.5.0")
        }
        desktopMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.5.0")

            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.flyhigh.os.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.flyhigh.os"
            packageVersion = "1.0.0"
        }
    }
}
