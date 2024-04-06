plugins {
    id("java")
}

group = "github.otowave"
version = "0.75"

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
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.test {
    useJUnitPlatform()
}