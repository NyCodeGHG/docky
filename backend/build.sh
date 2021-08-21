#!/usr/bin/env bash
#
#    Copyright 2021 NyCode
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.
#

archs=(amd64 arm64)

for arch in "${archs[@]}"; do
    echo "Building docky image for $arch"
    docker build . -t nycode/docky:"$arch" --build-arg ARCH="$arch" --build-arg OS=linux
    docker push nycode/docky:"$arch"
    if [ "$arch" = "amd64" ]; then
        docker tag nycode/docky:"$arch" nycode/docky:latest
        docker push nycode/docky:latest
    fi
done
