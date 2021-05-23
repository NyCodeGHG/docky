package de.nycode.docky.client

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class DockyClientTestJvm {

    @Test
    internal fun `Get Containers`() = runBlocking {
        val dockyClient = createMockDockyClient()
        val containers = dockyClient.getContainers()
        assertEquals(2, containers.size)
        assertEquals("redis:6.2.2-alpine", containers.first().image)
        assertEquals("mongo", containers[1].image)
    }

    private fun createMockDockyClient() = DockyClient(MockEngine, "http://localhost:8080") {
        httpClient {
            engine {
                addHandler {
                    when (it.url.encodedPath) {
                        "/containers" -> {
                            respond(
                                GetContainersData,
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                                }
                            )
                        }
                        else -> error("Unhandled ${it.url.fullPath}")
                    }
                }
            }
        }
    }
}
