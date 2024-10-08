plugins {
    id("java")
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.onepointltd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    create("intTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val intTestImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
}
val intTestRuntimeOnly by configurations.getting

configurations["intTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

val jacksonVersion = "2.17.1"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("org.junit-pioneer:junit-pioneer:2.2.0")
    // Analyzing math expressions
    implementation("com.ezylang:EvalEx:3.3.0")
    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.18.1")
    // CLI parsing
    // https://mvnrepository.com/artifact/commons-cli/commons-cli
    implementation("commons-cli:commons-cli:1.8.0")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")

    // https://mvnrepository.com/artifact/io.dropwizard/dropwizard-core
    implementation("io.dropwizard:dropwizard-core:4.0.7")

    intTestImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    intTestRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.test {
    useJUnitPlatform()
}

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["intTest"].output.classesDirs
    classpath = sourceSets["intTest"].runtimeClasspath
    shouldRunAfter("test")

    useJUnitPlatform()

    testLogging {
        events("passed")
    }
}

tasks.check { dependsOn(integrationTest) }

//tasks.jar {
//    manifest.attributes["Main-Class"] = "com.onepointltd.Main"
//    val dependencies = configurations
//        .runtimeClasspath
//        .get()
//        .map(::zipTree) // OR .map { zipTree(it) }
//    from(dependencies)
//    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//}

tasks.shadowJar {
    mergeServiceFiles()
    exclude (listOf("META-INF/*.DSA", "META-INF/*.RSA", "META-INF/*.SF"))
    manifest {
        attributes (mapOf("Main-Class" to "com.onepointltd.Main"))
    }
}

spotless {
    java {
        importOrder() // standard import order
        removeUnusedImports()
        googleJavaFormat()
    }
}