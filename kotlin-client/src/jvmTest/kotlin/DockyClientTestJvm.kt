package de.nycode.docky.client

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
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

    @Test
    internal fun `Restart Container`() = runBlocking {
        val dockyClient = createMockDockyClient()
        val containers = dockyClient.getContainers()
        dockyClient.restartContainer(containers.first())
    }

    @Test
    internal fun `Redeploy Container`() = runBlocking {
        val dockyClient = createMockDockyClient()
        val containers = dockyClient.getContainers()
        dockyClient.redeployContainer(containers.first())
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
                        "/restart/18fb8102af6985cde84637f5a9bcbda468d7226048b733c8808ea0ca20a4a593" -> {
                            respondOk()
                        }
                        "/redeploy/18fb8102af6985cde84637f5a9bcbda468d7226048b733c8808ea0ca20a4a593" -> {
                            respondOk()
                        }
                        else -> error("Unhandled ${it.url.fullPath}")
                    }
                }
            }
        }
    }
}
