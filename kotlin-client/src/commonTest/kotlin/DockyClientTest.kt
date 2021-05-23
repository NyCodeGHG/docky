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
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertTrue

internal class DockyClientTest {

    @Test
    @JsName("Create_Docky_Client")
    fun `Create Docky Client`() {
        val dockyClient = DockyClient(MockEngine, "http://localhost:8080") {
            httpClient {
                engine {
                    addHandler {
                        respond("Hi")
                    }
                }
            }
        }

        assertTrue(dockyClient.httpClient.engine is MockEngine)
    }
}
