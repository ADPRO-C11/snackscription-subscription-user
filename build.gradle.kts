plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.sonarqube") version "4.4.1.3373"
}

group = "snackscription"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

sonar {
	properties {
		property("sonar.projectKey", "ADPRO-C11_snackscription-subscription-user")
		property("sonar.organization", "adpro-c11")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	filter {
		excludeTestsMatching("*FunctionalTest")
	}

	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
}