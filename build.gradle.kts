group = "github.otowave"
version = "0.82"

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("net.bramp.ffmpeg:ffmpeg:0.8.0")
    implementation("com.github.gotson:webp-imageio:0.2.2")

    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
}

tasks.jar {
    manifest {
        attributes["Project-Version"] = version
        attributes["Main-Class"] = "github.otowave.api.Main"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}