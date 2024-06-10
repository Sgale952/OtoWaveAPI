group = "github.otowave.api"
version = "0.9.3"

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
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
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.postgresql:r2dbc-postgresql")

    implementation("org.springframework.boot:spring-boot-starter-security:3.1.0")
    implementation ("org.springframework.security:spring-security-web:6.0.0")
    implementation ("org.springframework.security:spring-security-config:6.0.0")

    implementation("io.jsonwebtoken:jjwt-api:0.11.1")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.1")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.1")

    implementation("com.github.gotson:webp-imageio:0.2.2")
    compileOnly ("org.projectlombok:lombok:1.18.32")
    annotationProcessor ("org.projectlombok:lombok:1.18.32")

}

tasks.jar {
    manifest {
        attributes["Project-Version"] = version
        attributes["Main-Class"] = "github.otowave.api.OtoWaveApi"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}