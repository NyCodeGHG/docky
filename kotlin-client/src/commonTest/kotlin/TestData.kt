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

const val GetContainersData = """
    [
  {
    "Id": "18fb8102af6985cde84637f5a9bcbda468d7226048b733c8808ea0ca20a4a593",
    "Names": [
      "/discord-bot_redis_1"
    ],
    "Image": "redis:6.2.2-alpine",
    "ImageID": "sha256:554d20f2036575e9d396d422c1569277738aa170aefcf88febbf08442cee0a08",
    "Command": "docker-entrypoint.sh redis-server",
    "Created": 1621769101,
    "Ports": [
      {
        "IP": "0.0.0.0",
        "PrivatePort": 6379,
        "PublicPort": 6379,
        "Type": "tcp"
      },
      {
        "IP": "::",
        "PrivatePort": 6379,
        "PublicPort": 6379,
        "Type": "tcp"
      }
    ],
    "Labels": {
      "com.docker.compose.config-hash": "002c0e46da0df28577a9a01c5059e6c4fab72e5f99b52a967039452fac7179bb",
      "com.docker.compose.container-number": "1",
      "com.docker.compose.oneoff": "False",
      "com.docker.compose.project": "discord-bot",
      "com.docker.compose.project.config_files": "C:\\Users\\Nico\\IdeaProjects\\butils-support-bot\\discord-bot\\docker-compose.dev.yml",
      "com.docker.compose.project.working_dir": "C:\\Users\\Nico\\IdeaProjects\\butils-support-bot\\discord-bot",
      "com.docker.compose.service": "redis",
      "com.docker.compose.version": "1.29.1",
      "desktop.docker.io/binds/0/Source": "9b50f41d3721e1c94aab65dff5da4276039fd1745392787b8427b3d25fbb4ffb",
      "desktop.docker.io/binds/0/SourceKind": "hostFile",
      "desktop.docker.io/binds/0/Target": "/data"
    },
    "State": "running",
    "Status": "Up 3 hours",
    "HostConfig": {
      "NetworkMode": "discord-bot_default"
    },
    "NetworkSettings": {
      "Networks": {
        "discord-bot_default": {
          "IPAMConfig": null,
          "Links": null,
          "Aliases": null,
          "NetworkID": "3bc19a1be5abed556443fb09561170d59e66c6bc55f8009a3c0eb3b11f3ccb51",
          "EndpointID": "f53117122987a0fc6fb88ad5763d4185f33e12492b4ccc10604bd2e098a6af79",
          "Gateway": "172.22.0.1",
          "IPAddress": "172.22.0.3",
          "IPPrefixLen": 16,
          "IPv6Gateway": "",
          "GlobalIPv6Address": "",
          "GlobalIPv6PrefixLen": 0,
          "MacAddress": "02:42:ac:16:00:03",
          "DriverOpts": null
        }
      }
    },
    "Mounts": [
      {
        "Type": "volume",
        "Name": "9b50f41d3721e1c94aab65dff5da4276039fd1745392787b8427b3d25fbb4ffb",
        "Source": "/var/lib/docker/volumes/9b50f41d3721e1c94aab65dff5da4276039fd1745392787b8427b3d25fbb4ffb/_data",
        "Destination": "/data",
        "Driver": "local",
        "Mode": "rw",
        "RW": true,
        "Propagation": ""
      }
    ]
  },
  {
    "Id": "d448b89bf92822b9f0501e05a12d63fb170f5c13aeaccc321c586c6a455342b0",
    "Names": [
      "/discord-bot_database_1"
    ],
    "Image": "mongo",
    "ImageID": "sha256:4616ab87e79ca4a4475211cf3f69eb40267c3b041f45b5bcecade8191fe2d97e",
    "Command": "docker-entrypoint.sh mongod",
    "Created": 1621769101,
    "Ports": [
      {
        "IP": "0.0.0.0",
        "PrivatePort": 27017,
        "PublicPort": 27017,
        "Type": "tcp"
      },
      {
        "IP": "::",
        "PrivatePort": 27017,
        "PublicPort": 27017,
        "Type": "tcp"
      }
    ],
    "Labels": {
      "com.docker.compose.config-hash": "304c4834712e11293f4a393d06cb4e2c3abac98814c3af55001a1377a3f280c5",
      "com.docker.compose.container-number": "1",
      "com.docker.compose.oneoff": "False",
      "com.docker.compose.project": "discord-bot",
      "com.docker.compose.project.config_files": "C:\\Users\\Nico\\IdeaProjects\\butils-support-bot\\discord-bot\\docker-compose.dev.yml",
      "com.docker.compose.project.working_dir": "C:\\Users\\Nico\\IdeaProjects\\butils-support-bot\\discord-bot",
      "com.docker.compose.service": "database",
      "com.docker.compose.version": "1.29.1",
      "desktop.docker.io/binds/1/Source": "369f5f5331ea1767faf41e2af20f2ae4898eb333582859ba8b002bdeff2827da",
      "desktop.docker.io/binds/1/SourceKind": "hostFile",
      "desktop.docker.io/binds/1/Target": "/data/configdb"
    },
    "State": "running",
    "Status": "Up 3 hours",
    "HostConfig": {
      "NetworkMode": "discord-bot_default"
    },
    "NetworkSettings": {
      "Networks": {
        "discord-bot_default": {
          "IPAMConfig": null,
          "Links": null,
          "Aliases": null,
          "NetworkID": "3bc19a1be5abed556443fb09561170d59e66c6bc55f8009a3c0eb3b11f3ccb51",
          "EndpointID": "1d4c09ff0394c93197c264e427eeb70db17b39d5067c7979f40c94723a4a82c3",
          "Gateway": "172.22.0.1",
          "IPAddress": "172.22.0.2",
          "IPPrefixLen": 16,
          "IPv6Gateway": "",
          "GlobalIPv6Address": "",
          "GlobalIPv6PrefixLen": 0,
          "MacAddress": "02:42:ac:16:00:02",
          "DriverOpts": null
        }
      }
    },
    "Mounts": [
      {
        "Type": "volume",
        "Name": "369f5f5331ea1767faf41e2af20f2ae4898eb333582859ba8b002bdeff2827da",
        "Source": "/var/lib/docker/volumes/369f5f5331ea1767faf41e2af20f2ae4898eb333582859ba8b002bdeff2827da/_data",
        "Destination": "/data/configdb",
        "Driver": "local",
        "Mode": "rw",
        "RW": true,
        "Propagation": ""
      },
      {
        "Type": "volume",
        "Name": "discord-bot_mongo_data",
        "Source": "/var/lib/docker/volumes/discord-bot_mongo_data/_data",
        "Destination": "/data/db",
        "Driver": "local",
        "Mode": "rw",
        "RW": true,
        "Propagation": ""
      }
    ]
  }
]
"""
