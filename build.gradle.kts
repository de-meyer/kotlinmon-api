plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.serialization") version "1.9.0"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.api.kotlinmon"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api:${property("jakartaVersion")}")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.github.resilience4j:resilience4j-kotlin:${property("resilience4jVersion")}")
    implementation("io.github.resilience4j:resilience4j-ratelimiter:${property("resilience4jVersion")}")
    implementation("io.github.resilience4j:resilience4j-retry:${property("resilience4jVersion")}")
    implementation("io.github.resilience4j:resilience4j-circuitbreaker:${property("resilience4jVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.ktor:ktor-client-core:${property("ktorClientVersion")}")
    implementation("io.ktor:ktor-client-cio:${property("ktorClientVersion")}") // CIO is an engine for Ktor
    implementation("io.ktor:ktor-client-content-negotiation:${property("ktorClientVersion")}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${property("ktorClientVersion")}")
    implementation("org.reactivestreams:reactive-streams:${property("reactiveStreamsVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${property("kotlinxCoroutinesVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${property("kotlinxSerializationVersion")}")
    implementation("io.projectreactor:reactor-core:${property("reactorCoreVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("ch.qos.logback:logback-classic:${property("logbackVersion")}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
