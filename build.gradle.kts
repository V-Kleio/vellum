plugins {
    `java-library`
    `maven-publish`
    checkstyle
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

tasks.register<JavaExec>("runExample") {
    group = "application"
    description = "Run Vellum example application"

    dependsOn(":vellum-core:classes")

    classpath = project(":vellum-core").sourceSets.main.get().runtimeClasspath
    mainClass.set("io.vellum.core.examples.Example")

    jvmArgs = listOf(
        "-Djava.awt.headless=false",
        "-Dorg.lwjgl.util.Debug=true"
    )

    standardInput = System.`in`
    workingDir = rootDir
}