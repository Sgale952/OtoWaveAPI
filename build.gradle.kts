plugins {
    id("java")
}

group = "github.otowave"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")

    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("org.eclipse.jetty:jetty-xml:12.0.6")
    implementation("org.eclipse.jetty:jetty-http:12.0.6")
    implementation("org.eclipse.jetty:jetty-server:12.0.6")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.test {
    useJUnitPlatform()
}