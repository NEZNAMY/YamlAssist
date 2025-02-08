plugins {
    `java-library`
    `maven-publish`
}

group = "me.neznamy"
version = "1.0.8"

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi("org.yaml", "snakeyaml", "1.27")

    testImplementation("org.yaml", "snakeyaml", "1.27")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}
