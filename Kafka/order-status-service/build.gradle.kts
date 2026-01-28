plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    java
}
dependencies {
    implementation(project(":common"))

    // Spring Boot Core + Context
    implementation("org.springframework.boot:spring-boot-starter")

    // Для web-контроллеров (только в order-service)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
