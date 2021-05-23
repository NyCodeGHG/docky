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

data class DockyHost(
    val protocol: Protocol,
    val host: String,
    val port: Int
) {
    override fun toString(): String {
        return "${protocol.name.lowercase()}://$host:$port"
    }
}

// https://regex101.com/r/XzFwuj/1
private val hostnameRegex = "(https|http)://([0-9.a-zA-Z_]+)(?::([0-9]+))?".toRegex()
internal const val DEFAULT_PORT = 8080

internal fun String.toDockyHost(): DockyHost? {
    val (protocol, host, port: String?) = (hostnameRegex.matchEntire(this) ?: return null).destructured
    return DockyHost(Protocol.valueOf(protocol.uppercase()), host, port.toIntOrNull() ?: DEFAULT_PORT)
}


enum class Protocol {
    HTTP,
    HTTPS;

    override fun toString(): String {
        return name.lowercase()
    }
}
