package handlers

import (
	"encoding/json"
	"github.com/NyCodeGHG/docky/connection"
	"net/http"
)

func ContainersHandler(w http.ResponseWriter, _ *http.Request) {
	containers, err := connection.GetAllContainers()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	containerJson, _ := json.Marshal(containers)

	_, err = w.Write(containerJson)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
