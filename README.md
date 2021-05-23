# docky

Small http server written in Go for accessing the docker daemon externally.

## Client Libraries

### Kotlin

There is a Kotlin multiplatform library available for accessing docky via http. It's available
on [Maven Central](https://search.maven.org/artifact/de.nycode/docky-kotlin-client/1.0.0/pom).

**Additionally a ktor engine dependency is needed.**

#### Standalone Kotlin

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("de.nycode", "docky-kotlin-client", "1.0.0")
    implementation("io.ktor", "ktor-client-okhttp", "1.5.4")
}
```

#### Multiplatform Project

```kotlin
repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("de.nycode", "docky-kotlin-client", "1.0.0")
                implementation("io.ktor", "ktor-client-cio", "1.5.4")
            }
        }
    }
}
```

#### Maven ([please don't use maven](https://gradle.is.better.than.maven.cf))

```xml

<dependency>
    <groupId>de.nycode</groupId>
    <artifactId>docky-kotlin-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### Usage
```kotlin
// Use your selected engine here
val dockyClient = DockyClient(OkHttp, "https://docky.nycode.de:443")

// Get all running containers
val containers: List<Container> = dockyClient.getContainers()

// Restart a random container
dockyClient.restartContainer(containers.random())

// Redeploy a random container
dockyClient.redeployContainer(containers.random())
```
