plugins {
    java
    idea
    alias(libs.plugins.moddev)
}

val modId = "glyphs"

base {
    archivesName.set(libs.versions.minecraft.map { "$modId-$it" })
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

neoForge {
    version = libs.versions.neoforge

    parchment.mappingsVersion = libs.versions.parchment
    parchment.minecraftVersion = "1.21"

    runs {
        register("client") {
            client()
        }
        register("server") {
            server()
            programArgument("--nogui")
        }
        register("data") {
            data()
            programArguments.addAll(
                "--mod", modId,
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }
    }

    mods {
        register(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main.get().resources { srcDir("src/generated/resources") }

repositories {
    maven(url = "https://maven.neoforged.net/releases")
    maven(url = "https://maven.teamresourceful.com/repository/maven-public")
    mavenLocal()
}

dependencies {
    implementation(libs.rlib)
    implementation(libs.olympus)

    jarJar(libs.rlib)
    jarJar(libs.olympus)

    compileOnly("com.teamresourceful:bytecodecs:1.1.2")
}

java {
    withSourcesJar()
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    filesMatching(listOf("META-INF/neoforge.mods.toml")) {
        expand("version" to project.version)
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true

        excludeDirs.add(file("run"))
    }
}