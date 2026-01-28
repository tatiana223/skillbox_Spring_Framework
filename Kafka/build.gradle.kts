plugins {
    id("org.springframework.boot") version "3.3.1" apply false
    id("io.spring.dependency-management") version "1.1.5" apply false
    java
}

subprojects {

    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    group = "com.example"
    version = "1.0"

    dependencies {
        // Lombok
        compileOnly("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")

        // Lombok for tests
        testCompileOnly("org.projectlombok:lombok:1.18.30")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    }
}
