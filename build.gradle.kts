plugins {
    kotlin("multiplatform") version "2.0.20"
    id("org.jetbrains.dokka") version "1.9.20"
    id("io.kotest.multiplatform") version "5.9.1"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    `maven-publish`
    signing
}

group = "io.github.devngho"
version = "0.2.3"

repositories {
    mavenCentral()
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

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

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")

    when {
        hostOs == "Mac OS X" -> macosX64()
        hostOs == "Linux" -> linuxX64()
        isMingwX64 -> mingwX64()
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    jvm()

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
    getByName("signKotlinMultiplatformPublication") {
        dependsOn("publishJvmPublicationToSonatypeRepository", "publishJvmPublicationToMavenLocal")
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTargets = mutableListOf<String>()

    if (hostOs == "Mac OS X") nativeTargets.add("MacosX64")
    if (hostOs == "Linux") nativeTargets.add("LinuxX64")
    if (isMingwX64) nativeTargets.add("MingwX64")

    nativeTargets.forEach { target ->
        getByName("sign${target}Publication") {
            dependsOn(
                "publishJvmPublicationToSonatypeRepository",
                "publishJvmPublicationToMavenLocal",
                "publishKotlinMultiplatformPublicationToMavenLocal",
                "publishKotlinMultiplatformPublicationToSonatypeRepository"
            )
        }
    }

    named<Test>("jvmTest") {
        useJUnitPlatform()
    }
}