/*
 *    Copyright 2021 NyCode
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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

    private val baseMockUrl = "http://localhost:8080"
    private fun createMockDockyClient() = DockyClient(MockEngine, baseMockUrl) {
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
