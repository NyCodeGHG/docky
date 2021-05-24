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
	"encoding/base64"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/NyCodeGHG/docky/connection"
	"log"
	"net/http"
	"strings"
)

type RedeployBody struct {
	Authentication string
}

func RedeployHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	containerName := strings.TrimPrefix(r.URL.Path, "/redeploy/")
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

	var authentication RedeployBody

	err = json.NewDecoder(r.Body).Decode(&authentication)

	if err == nil {
		auth, err := base64.StdEncoding.DecodeString(authentication.Authentication)
		if err == nil {
			username := strings.Split(string(auth), ":")
			log.Printf("Using Username %s and Password *******", username)
		}
	} else {
		fmt.Println(err.Error())
	}

	w.WriteHeader(200)
	go func() {
		err = connection.Redeploy(container, authentication.Authentication)
		if err != nil {
			fmt.Println("Received error: " + err.Error())
		} else {
			fmt.Println("Successfully redeployed container!")
		}
	}()
}
