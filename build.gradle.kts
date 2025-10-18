buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.11.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
        classpath("app.cash.sqldelight:gradle-plugin:2.1.0")
    }
}

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "2.2.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    id("com.android.library") version "8.11.0"
    id("app.cash.sqldelight") version "2.1.0"
    id("maven-publish")
}

version = "5.0.0-alpha4"
group = "project.pipepipe"

kotlin {
    jvmToolchain(24)

    jvm {
    }

    androidTarget {
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
                implementation("io.ktor:ktor-client-core:3.3.0")
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.0")
                implementation("app.cash.sqldelight:runtime:2.1.0")
                implementation("app.cash.sqldelight:coroutines-extensions:2.1.0")
                implementation("com.fasterxml.jackson.core:jackson-core:2.20.0")
                implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
                implementation("io.github.reactivecircus.cache4k:cache4k:0.14.0")
                implementation("com.russhwolf:multiplatform-settings:1.3.0")
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        jvmMain {
            dependencies {
            }
        }
        jvmTest {
            dependencies {
            }
        }
    }
}

android {
    namespace = "project.pipepipe"
    compileSdk = 36
    defaultConfig {
        minSdk = 26
    }
}

publishing {
    repositories {
        maven {
            name = "local"
            url = uri("${rootProject.projectDir}/../local-maven")
        }
    }
}
