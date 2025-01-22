@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform") version "2.1.0"
    id("org.jetbrains.dokka") version "2.0.0"
    id("io.kotest.multiplatform") version "5.9.1"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    `maven-publish`
    signing
}

group = "io.github.devngho"
version = "0.2.4"

repositories {
    mavenCentral()
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

// copied from ionspin/kotlin-multiplatform-bignum (at build.gradle.kts), Apache 2.0
enum class HostOs {
    LINUX, WINDOWS, MAC
}


fun getHostOsName(): HostOs {
    val target = System.getProperty("os.name")
    if (target == "Linux") return HostOs.LINUX
    if (target.startsWith("Windows")) return HostOs.WINDOWS
    if (target.startsWith("Mac")) return HostOs.MAC
    throw GradleException("Unknown OS: $target")
}

val hostOs = getHostOsName()
// copy end

kotlin {
    publishing {
        signing {
            sign(publishing.publications)
        }

        repositories {
            val id: String =
                if (project.hasProperty("repoUsername")) project.property("repoUsername") as String
                else System.getenv("repoUsername")
            val pw: String =
                if (project.hasProperty("repoPassword")) project.property("repoPassword") as String
                else System.getenv("repoPassword")
            if (!version.toString().endsWith("SNAPSHOT")) {
                val repositoryId =
                    System.getenv("SONATYPE_REPOSITORY_ID")

                maven("https://s01.oss.sonatype.org/service/local/staging/deployByRepositoryId/${repositoryId}/") {
                    name = "Sonatype"
                    credentials {
                        username = id
                        password = pw
                    }
                }
            } else {
                maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
                    name = "Sonatype"
                    credentials {
                        username = id
                        password = pw
                    }
                }
            }
        }

        publications.withType(MavenPublication::class) {
            groupId = project.group as String?
            artifactId = "kmarkdown"
            version = project.version as String?

            artifact(tasks["javadocJar"])

            pom {
                name.set(artifactId)
                description.set("Pure Kotlin Markdown builder")
                url.set("https://github.com/devngho/kmarkdown")


                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/devngho/kmarkdown/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("devngho")
                        name.set("devngho")
                        email.set("yjh135908@gmail.com")
                    }
                }
                scm {
                    connection.set("https://github.com/devngho/kmarkdown.git")
                    developerConnection.set("https://github.com/devngho/kmarkdown.git")
                    url.set("https://github.com/devngho/kmarkdown")
                }
            }
        }
    }

    // copied from ionspin/kotlin-multiplatform-bignum (at build.gradle.kts), Apache 2.0
    // removed watchosDeviceArm64 and modified js
    js {
        nodejs()
        browser()
    }
    linuxX64()
    linuxArm64()
    androidNativeX64()
    androidNativeX86()
    androidNativeArm32()
    androidNativeArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    watchosArm32()
    watchosArm64()
    watchosX64()
    watchosSimulatorArm64()
    mingwX64()
    // copy end

    jvm()

    wasmJs {
        browser()
        nodejs()
        d8()
    }
    wasmWasi()

    sourceSets {
        val kotestVersion = "5.9.1"

        jvmTest.dependencies {
            implementation("io.kotest:kotest-framework-engine:$kotestVersion")
            implementation("io.kotest:kotest-assertions-core:$kotestVersion")
            implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(kotlin("reflect"))
        }

        applyDefaultHierarchyTemplate()
    }
}

tasks {
    // copied from ionspin/kotlin-multiplatform-bignum (at build.gradle.kts), Apache 2.0
    // fixed for correct task dependencies in this project
    all {
        if (hostOs == HostOs.MAC) {
            // macOS task dependencies
            // @formatter:off
            when (this.name) {
                "signAndroidNativeArm32Publication" -> { this.mustRunAfter("signAndroidNativeArm64Publication") }
                "signAndroidNativeArm64Publication" -> { this.mustRunAfter("signAndroidNativeX64Publication") }
                "signAndroidNativeX64Publication" -> { this.mustRunAfter("signAndroidNativeX86Publication") }
                "signAndroidNativeX86Publication" -> { this.mustRunAfter("signJsPublication") }
                "signJsPublication" -> { this.mustRunAfter("signJvmPublication") }
                "signJvmPublication" -> { this.mustRunAfter("signKotlinMultiplatformPublication") }
                "signKotlinMultiplatformPublication" -> { this.mustRunAfter("signLinuxArm64Publication") }
                "signLinuxArm64Publication" -> { this.mustRunAfter("signLinuxX64Publication") }
                "signLinuxX64Publication" -> { this.mustRunAfter("signWasmJsPublication") }
                "signWasmJsPublication" -> { this.mustRunAfter("signWasmWasiPublication") }
                "signWasmWasiPublication" -> { this.mustRunAfter("signMingwX64Publication") }
                "signMingwX64Publication" -> { this.mustRunAfter("signIosArm64Publication") }
                "signIosArm64Publication" -> { this.mustRunAfter("signIosSimulatorArm64Publication") }
                "signIosSimulatorArm64Publication" -> { this.mustRunAfter("signIosX64Publication") }
                "signIosX64Publication" -> { this.mustRunAfter("signMacosArm64Publication") }
                "signMacosArm64Publication" -> { this.mustRunAfter("signMacosX64Publication") }
                "signMacosX64Publication" -> { this.mustRunAfter("signTvosArm64Publication") }
                "signTvosArm64Publication" -> { this.mustRunAfter("signTvosSimulatorArm64Publication") }
                "signTvosSimulatorArm64Publication" -> { this.mustRunAfter("signTvosX64Publication") }
                "signTvosX64Publication" -> { this.mustRunAfter("signWatchosArm32Publication") }
                "signWatchosArm32Publication" -> { this.mustRunAfter("signWatchosArm64Publication") }
                "signWatchosArm64Publication" -> { this.mustRunAfter("signWatchosSimulatorArm64Publication") }
                "signWatchosSimulatorArm64Publication" -> { this.mustRunAfter("signWatchosX64Publication") }
            }
            // @formatter:on

            if (this.name.startsWith("publish")) {
                this.mustRunAfter("signAndroidNativeArm32Publication")
                this.mustRunAfter("signAndroidNativeArm64Publication")
                this.mustRunAfter("signAndroidNativeX64Publication")
                this.mustRunAfter("signAndroidNativeX86Publication")
                this.mustRunAfter("signJsPublication")
                this.mustRunAfter("signJvmPublication")
                this.mustRunAfter("signKotlinMultiplatformPublication")
                this.mustRunAfter("signLinuxArm64Publication")
                this.mustRunAfter("signLinuxX64Publication")
                this.mustRunAfter("signWasmJsPublication")
                this.mustRunAfter("signWasmWasiPublication")
                this.mustRunAfter("signMingwX64Publication")
                this.mustRunAfter("signIosArm64Publication")
                this.mustRunAfter("signIosSimulatorArm64Publication")
                this.mustRunAfter("signIosX64Publication")
                this.mustRunAfter("signMacosArm64Publication")
                this.mustRunAfter("signMacosX64Publication")
                this.mustRunAfter("signTvosArm64Publication")
                this.mustRunAfter("signTvosSimulatorArm64Publication")
                this.mustRunAfter("signTvosX64Publication")
                this.mustRunAfter("signWatchosArm32Publication")
                this.mustRunAfter("signWatchosArm64Publication")
                this.mustRunAfter("signWatchosSimulatorArm64Publication")
                this.mustRunAfter("signWatchosX64Publication")
            }

            if (this.name.startsWith("compileTest")) {
                this.mustRunAfter("signAndroidNativeArm32Publication")
                this.mustRunAfter("signAndroidNativeArm64Publication")
                this.mustRunAfter("signAndroidNativeX64Publication")
                this.mustRunAfter("signAndroidNativeX86Publication")
                this.mustRunAfter("signJsPublication")
                this.mustRunAfter("signJvmPublication")
                this.mustRunAfter("signKotlinMultiplatformPublication")
                this.mustRunAfter("signLinuxArm64Publication")
                this.mustRunAfter("signLinuxX64Publication")
                this.mustRunAfter("signWasmJsPublication")
                this.mustRunAfter("signWasmWasiPublication")
                this.mustRunAfter("signMingwX64Publication")
                this.mustRunAfter("signIosArm64Publication")
                this.mustRunAfter("signIosSimulatorArm64Publication")
                this.mustRunAfter("signIosX64Publication")
                this.mustRunAfter("signMacosArm64Publication")
                this.mustRunAfter("signMacosX64Publication")
                this.mustRunAfter("signTvosArm64Publication")
                this.mustRunAfter("signTvosSimulatorArm64Publication")
                this.mustRunAfter("signTvosX64Publication")
                this.mustRunAfter("signWatchosArm32Publication")
                this.mustRunAfter("signWatchosArm64Publication")
                this.mustRunAfter("signWatchosSimulatorArm64Publication")
            }
            if (this.name.startsWith("linkDebugTest")) {
                this.mustRunAfter("signAndroidNativeArm32Publication")
                this.mustRunAfter("signAndroidNativeArm64Publication")
                this.mustRunAfter("signAndroidNativeX64Publication")
                this.mustRunAfter("signAndroidNativeX86Publication")
                this.mustRunAfter("signJsPublication")
                this.mustRunAfter("signJvmPublication")
                this.mustRunAfter("signKotlinMultiplatformPublication")
                this.mustRunAfter("signLinuxArm64Publication")
                this.mustRunAfter("signLinuxX64Publication")
                this.mustRunAfter("signWasmJsPublication")
                this.mustRunAfter("signWasmWasiPublication")
                this.mustRunAfter("signMingwX64Publication")
                this.mustRunAfter("signIosArm64Publication")
                this.mustRunAfter("signIosSimulatorArm64Publication")
                this.mustRunAfter("signIosX64Publication")
                this.mustRunAfter("signMacosArm64Publication")
                this.mustRunAfter("signMacosX64Publication")
                this.mustRunAfter("signTvosArm64Publication")
                this.mustRunAfter("signTvosSimulatorArm64Publication")
                this.mustRunAfter("signTvosX64Publication")
                this.mustRunAfter("signWatchosArm32Publication")
                this.mustRunAfter("signWatchosArm64Publication")
                this.mustRunAfter("signWatchosSimulatorArm64Publication")
            }
        } else {
            // Windows || linux task dependecies

            // @formatter:off
            when (this.name) {
                "signAndroidNativeArm32Publication" -> { this.mustRunAfter("signAndroidNativeArm64Publication") }
                "signAndroidNativeArm64Publication" -> { this.mustRunAfter("signAndroidNativeX64Publication") }
                "signAndroidNativeX64Publication" -> { this.mustRunAfter("signAndroidNativeX86Publication") }
                "signAndroidNativeX86Publication" -> { this.mustRunAfter("signJsPublication") }
                "signJsPublication" -> { this.mustRunAfter("signJvmPublication") }
                "signJvmPublication" -> { this.mustRunAfter("signKotlinMultiplatformPublication") }
                "signKotlinMultiplatformPublication" -> { this.mustRunAfter("signLinuxArm64Publication") }
                "signLinuxArm64Publication" -> { this.mustRunAfter("signLinuxX64Publication") }
                "signLinuxX64Publication" -> { this.mustRunAfter("signWasmJsPublication") }
                "signWasmJsPublication" -> { this.mustRunAfter("signWasmWasiPublication") }
                "signWasmWasiPublication" -> { this.mustRunAfter("signMingwX64Publication") }
            }
            // @formatter:on

            if (this.name.startsWith("publish")) {
                this.mustRunAfter("signAndroidNativeArm32Publication")
                this.mustRunAfter("signAndroidNativeArm64Publication")
                this.mustRunAfter("signAndroidNativeX64Publication")
                this.mustRunAfter("signAndroidNativeX86Publication")
                this.mustRunAfter("signJsPublication")
                this.mustRunAfter("signJvmPublication")
                this.mustRunAfter("signKotlinMultiplatformPublication")
                this.mustRunAfter("signLinuxArm64Publication")
                this.mustRunAfter("signLinuxX64Publication")
                this.mustRunAfter("signWasmJsPublication")
                this.mustRunAfter("signWasmWasiPublication")
                this.mustRunAfter("signMingwX64Publication")
            }

            if (this.name.startsWith("compileTest")) {
                this.mustRunAfter("signAndroidNativeArm32Publication")
                this.mustRunAfter("signAndroidNativeArm64Publication")
                this.mustRunAfter("signAndroidNativeX64Publication")
                this.mustRunAfter("signAndroidNativeX86Publication")
                this.mustRunAfter("signJsPublication")
                this.mustRunAfter("signJvmPublication")
                this.mustRunAfter("signKotlinMultiplatformPublication")
                this.mustRunAfter("signLinuxArm64Publication")
                this.mustRunAfter("signLinuxX64Publication")
                this.mustRunAfter("signWasmJsPublication")
                this.mustRunAfter("signWasmWasiPublication")
                this.mustRunAfter("signMingwX64Publication")
            }

            if (this.name.startsWith("linkDebugTest")) {
                this.mustRunAfter("signAndroidNativeArm32Publication")
                this.mustRunAfter("signAndroidNativeArm64Publication")
                this.mustRunAfter("signAndroidNativeX64Publication")
                this.mustRunAfter("signAndroidNativeX86Publication")
                this.mustRunAfter("signJsPublication")
                this.mustRunAfter("signJvmPublication")
                this.mustRunAfter("signKotlinMultiplatformPublication")
                this.mustRunAfter("signLinuxArm64Publication")
                this.mustRunAfter("signLinuxX64Publication")
                this.mustRunAfter("signWasmJsPublication")
                this.mustRunAfter("signWasmWasiPublication")
                this.mustRunAfter("signMingwX64Publication")
            }
        }
    }
    // copy end

    named<Test>("jvmTest") {
        useJUnitPlatform()
    }
}