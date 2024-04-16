group = "github.otowave"
version = "0.82"

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")

    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.zoomba-lang:spark-core:3.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("net.bramp.ffmpeg:ffmpeg:0.8.0")
    implementation("com.github.gotson:webp-imageio:0.2.2")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.2")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "github.otowave.api.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}