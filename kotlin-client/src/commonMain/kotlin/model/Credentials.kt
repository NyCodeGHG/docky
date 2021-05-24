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

package de.nycode.docky.client.model

import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val username: String,
    val password: String
) {
    @OptIn(InternalAPI::class)
    override fun toString(): String {
        return "$username:$password".encodeBase64()
    }
}
