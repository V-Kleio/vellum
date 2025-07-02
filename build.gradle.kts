plugins {
    `java-library`
    `maven-publish`
    checkstyle
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

    // Java configuration
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
        withSourcesJar()
        withJavadocJar()
    }

    // Checkstyle configuration
    checkstyle {
        toolVersion = "10.12.4"
        configFile = rootProject.file("config/checkstyle/checkstyle.xml")
        isIgnoreFailures = false
        maxWarnings = 0
    }

    // Dependency Check configuration
    configure<org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension> {
        formats = listOf("HTML")
        suppressionFile = rootProject.file("config/dependency-check-suppressions.xml").path
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    tasks.named<Javadoc>("javadoc") {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }
}