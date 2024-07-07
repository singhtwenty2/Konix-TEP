val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project
val commons_codec_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "com.singhtwenty2"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// External Dependencies...
dependencies {
    // JDBC connector
    runtimeOnly("mysql:mysql-connector-java:8.0.28")
    // Hashing
    implementation("commons-codec:commons-codec:$commons_codec_version")
    // Kotlin standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")
    // Coroutine support
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    // Sendgrid email client
    implementation("com.sendgrid:sendgrid-java:4.7.4")
    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.12")
}

// Task to generate JAR
tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.withType<Test> {
    useJUnitPlatform()
    ignoreFailures = true
}

