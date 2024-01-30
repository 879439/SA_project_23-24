

plugins {
    id("java")
    id("application")
}


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
application {
    mainClass.set( "org.example.Main")
}
tasks.jar {
    manifest {
        attributes(mapOf(
                "Main-Class" to application.mainClass.get()
        ))
    }
}
tasks.test {
    useJUnitPlatform()
}