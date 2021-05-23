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

package handlers

import (
	"errors"
	"fmt"
	"github.com/NyCodeGHG/docky/connection"
	"net/http"
	"strings"
)

func RestartHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	containerName := strings.TrimPrefix(r.URL.Path, "/restart/")
	if strings.TrimSpace(containerName) == "" {
		http.Error(w, errors.New("invalid container id").Error(), http.StatusBadRequest)
		return
	}
	container, err := connection.GetContainer(containerName)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	if container == nil {
		http.Error(w, errors.New("container not found").Error(), http.StatusBadRequest)
		return
	}
	w.WriteHeader(200)
	go func() {
		err = connection.Restart(container)
		if err != nil {
			fmt.Println("Received error: " + err.Error())
		} else {
			fmt.Println("Successfully restarted container!")
		}
	}()
}
