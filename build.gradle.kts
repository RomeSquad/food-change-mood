plugins {
    kotlin("jvm") version "2.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.insert-koin:koin-core:4.0.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")


    // Google Truth
    testImplementation("com.google.truth:truth:1.4.2")

    // parameterized test
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")

    // mock K
    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}