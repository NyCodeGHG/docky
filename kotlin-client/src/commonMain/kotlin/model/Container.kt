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

import de.nycode.docky.client.serialization.InstantUnixTimestampSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Container(
    @SerialName("Id")
    val id: String,
    @SerialName("Names")
    val names: List<String>,
    @SerialName("Image")
    val image: String,
    @SerialName("ImageID")
    val imageID: String,
    @SerialName("Command")
    val command: String,
    @SerialName("Created")
    @Serializable(with = InstantUnixTimestampSerializer::class)
    val created: Instant,
    @SerialName("Ports")
    val ports: List<PortMapping>,
    @SerialName("Labels")
    val labels: Labels,
    @SerialName("State")
    val state: ContainerState,
    @SerialName("Status")
    val status: String,
    @SerialName("HostConfig")
    val hostConfig: Map<String, String>,
    @SerialName("Mounts")
    val mounts: List<Mount>
)
