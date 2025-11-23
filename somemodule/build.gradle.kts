import com.teamscale.TeamscaleUpload
import com.teamscale.extension.TeamscaleTaskExtension
import com.teamscale.reporting.testwise.TestwiseCoverageReport
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register

plugins {
    id("java")
    kotlin("jvm")
    id("com.teamscale") version "35.0.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
}

val testwiseCoverageReport by tasks.registering(TestwiseCoverageReport::class) {
    executionData(tasks.test)
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(testwiseCoverageReport)
    configure<JacocoTaskExtension> {
        includes = listOf("org.example.*")
    }
    configure<TeamscaleTaskExtension> {
        collectTestwiseCoverage = true
    }
}

teamscale {
    server {
        url = "http://localhost:8080/"
        userName = "admin"
        userAccessToken = System.getProperty("teamscale.access-token")
        project = "java-junit-test"
    }
}

tasks.register<TeamscaleUpload>("teamscaleTestUpload") {
    partition = "Unit Tests"
    from(tasks.named("testwiseCoverageReport"))
}

kotlin {
    jvmToolchain(21)
}