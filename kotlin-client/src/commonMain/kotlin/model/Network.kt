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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val name: String,
    @SerialName("NetworkID")
    val networkID: String,
    @SerialName("EndpointID")
    val endpointID: String,
    @SerialName("Gateway")
    val gateway: String,
    @SerialName("IPAddress")
    val ipAddress: String,
    @SerialName("IPPrefixLen")
    val ipPrefixLength: Int,
    @SerialName("IPv6Gateway")
    val ipv6GateWay: String,
    @SerialName("GlobalIPv6Address")
    val globalIpv6Address: String,
    @SerialName("GlobalIPv6PrefixLen")
    val globalIpv6PrefixLen: Int,
    @SerialName("MacAddress")
    val macAddress: String
)
