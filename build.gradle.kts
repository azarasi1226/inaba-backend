import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"

	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

allprojects {
	val axonVersion = "4.9.3"

	group = "inaba"
	version = "0.0.1-SNAPSHOT"

	apply(plugin = "kotlin")

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "21"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	dependencies {
		implementation(platform("org.axonframework:axon-bom:${axonVersion}"))
		implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
	}
}