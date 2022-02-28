plugins {
    `java-library`
    `maven-publish`
}

group = "me.neznamy"
version = "1.0.5"

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    compileOnlyApi("org.yaml", "snakeyaml", "1.27")
}

publishing {
    repositories {
        maven {
            name = "krypton"
            url = uri("https://repo.kryptonmc.org/releases")
            credentials(PasswordCredentials::class)
        }
    }
    publications.create<MavenPublication>("mavenJava") {
        from(components["java"])
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}
