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

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class DockyHostTest {

    @Test
    @JsName("Create_Docky_Host_From_String")
    fun `Create Docky Host From String`() {
        val hostname = "http://localhost:8081"
        val dockyHost = hostname.toDockyHost()
        assertNotNull(dockyHost)
        assertEquals("localhost", dockyHost.host)
        assertEquals(8081, dockyHost.port)
        assertEquals(Protocol.HTTP, dockyHost.protocol)
    }

    @Test
    @JsName("Create_Docky_Host_From_String_without_Port")
    fun `Create Docky Host From String without Port`() {
        val hostname = "https://localhost"
        val dockyHost = hostname.toDockyHost()
        assertNotNull(dockyHost)
        assertEquals("localhost", dockyHost.host)
        assertEquals(Protocol.HTTPS, dockyHost.protocol)
        assertEquals(DEFAULT_PORT, dockyHost.port)
    }
    
    @Test
    @JsName("Docky_Host_Create_should_fail")
    fun `Docky Host Create should fail`() {
        val hostname = "ftp://loca424%$:5ndsa"
        val dockyHost = hostname.toDockyHost()
        assertNull(dockyHost)
    }

    @Test
    @JsName("Docky_Host_Create_should_fail_with_empty_string")
    fun `Docky Host Create should fail with empty string`() {
        val hostname = ""
        val dockyHost = hostname.toDockyHost()
        assertNull(dockyHost)
    }
}
