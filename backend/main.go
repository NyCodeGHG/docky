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

package main

import (
	"fmt"
	"github.com/NyCodeGHG/docky/configuration"
	"github.com/NyCodeGHG/docky/handlers"
	"log"
	"net/http"
)

func initializeHandlers() {
	http.HandleFunc("/", handlers.RootHandler)
	http.HandleFunc("/containers/", handlers.ContainersHandler)
	http.HandleFunc("/redeploy/", handlers.RedeployHandler)
	http.HandleFunc("/restart/", handlers.RestartHandler)
}

func main() {
	config := configuration.ReadConfig()
	initializeHandlers()

	bindAddress := fmt.Sprintf("%s:%d", config.Host, config.Port)
	log.Printf("Listening on %s\n", bindAddress)
	log.Fatal(http.ListenAndServe(bindAddress, nil))
}
