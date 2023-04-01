plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.3.11"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.eintosti"
version = "0.1-ALPHA"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")

    implementation("dev.jorel:commandapi-shade:8.8.0")

    compileOnly("dev.jorel:commandapi-core:8.8.0")
    compileOnly("org.jetbrains:annotations:23.0.0")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    reobfJar {
        outputJar.set(layout.buildDirectory.file("libs/Chess.jar"))
    }

    shadowJar {
        val shadePath = "${project.group}.chess.util.external"

        relocate("dev.jorel", "$shadePath.commandapi")
    }

    configurations.archives.get().artifacts.removeIf { archive ->
        archive.name.contains("-")
    }
}

bukkit {
    name = "Chess"
    version = "${project.version}"
    main = "com.eintosti.chess.Chess"
    apiVersion = "1.19"

    authors = listOf("Trichtern")
}