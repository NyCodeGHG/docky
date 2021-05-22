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
