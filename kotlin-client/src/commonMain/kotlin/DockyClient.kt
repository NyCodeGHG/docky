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

import de.nycode.docky.client.model.Container
import de.nycode.docky.client.model.Credentials
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.features.UserAgent
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.js.JsName

/**
 * Client used for interacting with the Docky Backend via http
 */
class DockyClient<C : HttpClientEngineConfig> internal constructor(
    engine: HttpClientEngineFactory<C>,
    httpClientOptions: HttpClientConfig<C>.() -> Unit,
    private val dockyHost: DockyHost
) {

    internal val httpClient: HttpClient = HttpClient(engine) {
        httpClientOptions()
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(UserAgent) {
            agent = "docky-kotlin-client"
        }
    }

    /**
     * Get all containers from docky
     * @return all containers
     */
    suspend fun getContainers(): List<Container> {
        return httpClient.get(dockyHost) {
            url {
                path("containers")
            }
        }
    }

    /**
     * Redeploy a container
     * @param container the container to redeploy
     */
    suspend fun redeployContainer(container: Container, credentials: Credentials? = null) {
        return httpClient.post(dockyHost) {
            contentType(ContentType.Application.Json)
            if (credentials != null) {
                body = credentials
            }
            url {
                path("redeploy/${container.id}")
            }
        }
    }

    /**
     * Restart a container
     * @param container the container to restart
     */
    suspend fun restartContainer(container: Container) {
        return httpClient.get(dockyHost) {
            url {
                path("restart/${container.id}")
            }
        }
    }

}

/**
 * Creates a new [DockyClient] with the specified engine
 */
@OptIn(ExperimentalContracts::class)
@JsName("DockyClientBuilder")
fun <C : HttpClientEngineConfig> DockyClient(
    engine: HttpClientEngineFactory<C>,
    dockyHost: String,
    optionBuilder: DockyClientOptions<C>.() -> Unit = {}
): DockyClient<C> {
    contract {
        callsInPlace(optionBuilder, InvocationKind.EXACTLY_ONCE)
    }

    val options = DockyClientOptions<C>()
    options.apply(optionBuilder)

    val clientOptions: HttpClientConfig<C>.() -> Unit = {
        options.httpOptionBuilders.forEach { it() }
    }

    return DockyClient(engine, clientOptions, dockyHost.toDockyHost() ?: error("Unable to parse Docky Host"))
}
