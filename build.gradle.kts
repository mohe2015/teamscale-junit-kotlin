import com.teamscale.extension.TeamscaleTaskExtension
import com.teamscale.reporting.testwise.TestwiseCoverageReport
import com.teamscale.TeamscaleUpload

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
    forkEvery = 1
    useJUnitPlatform()
    finalizedBy(testwiseCoverageReport)
    configure<JacocoTaskExtension> {
        includes = listOf("org.example.*")
    }
    configure<TeamscaleTaskExtension> {
        collectTestwiseCoverage = true
    }
}

evaluationDependsOn("somemodule")
tasks.register<TeamscaleUpload>("teamscaleTestUpload") {
    partition = "Unit Tests"
    from(project("somemodule").tasks.named("testwiseCoverageReport"))
    from(tasks.named("testwiseCoverageReport"))
}

teamscale {
    server {
        url = "http://localhost:8080/"
        userName = "admin"
        userAccessToken = System.getProperty("teamscale.access-token")
        project = "java-junit-test"
    }
}


kotlin {
    jvmToolchain(21)
}