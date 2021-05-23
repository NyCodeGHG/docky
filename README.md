# docky

Small http server written in Go for accessing the docker daemon externally.

There is a Docker Image available on [Docker Hub](https://hub.docker.com/r/nycode/docky).

## Docker
```bash
docker run -v /var/run/docker.sock:/var/run/docker.sock -p 8080:8080 nycode/docky
```

## Docker Compose
```yaml
version: '3.7'

services:
  docky:
    image: nycode/docky
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock:ro
    ports:
      - 8080:8080
```

## Client Libraries

### Kotlin

There is a Kotlin multiplatform library available for accessing docky via http. It's available
on [Maven Central](https://search.maven.org/artifact/de.nycode/docky-kotlin-client/1.0.2/pom).

**Additionally a ktor engine dependency is needed.**

#### Standalone Kotlin

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("de.nycode", "docky-kotlin-client", "1.0.2")
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
                implementation("de.nycode", "docky-kotlin-client", "1.0.2")
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
    <version>1.0.2</version>
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
