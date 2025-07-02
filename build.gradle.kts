plugins {
    `java-library` apply false
    `maven-publish` apply false
    id("checkstyle") apply false
    id("org.owasp.dependencycheck") version "9.2.0" apply false
}

allprojects {
    group = "io.vellum"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "checkstyle")
    apply(plugin = "org.owasp.dependencycheck")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
        withSourcesJar()
        withJavadocJar()
    }

    checkstyle {
        toolVersion = "10.12.4"
        configFile = rootProject.file("config/checkstyle/checkstyle.xml")
        isIgnoreFailures = false
        maxWarnings = 0
    }

    dependencyCheck {
        format = "HTML"
        suppressionFile = rootProject.file("config/dependency-check-suppressions.xml").path
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
    
    tasks.named<Test>("test") {
        useJUnitPlatform()
    }
}