plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.3")
    implementation("org.springframework.security:spring-security-crypto")
    implementation("org.springframework.boot:spring-boot-starter-validation") // Optional, for validation annotations
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.itextpdf:itext7-core:7.1.9")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.json:json:20231013")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:javase:3.4.1")


}
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.5.2")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
