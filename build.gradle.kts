
plugins {
    kotlin("jvm") version "1.6.21"
    java
    idea
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.3.7"
}

group = "io.github.ixhbinphoenix"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.19-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}