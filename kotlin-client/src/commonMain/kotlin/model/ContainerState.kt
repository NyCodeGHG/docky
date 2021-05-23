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

/**
 * State of a container.
 */
@Serializable
enum class ContainerState {
    @SerialName("created")
    CREATED,

    @SerialName("restarting")
    RESTARTING,

    @SerialName("running")
    RUNNING,

    @SerialName("paused")
    PAUSED,

    @SerialName("exited")
    EXITED,

    @SerialName("dead")
    DEAD
}
