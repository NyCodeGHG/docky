package handlers

import (
	"errors"
	"fmt"
	"github.com/NyCodeGHG/docky/connection"
	"net/http"
	"strings"
)

func RestartHandler(w http.ResponseWriter, r *http.Request) {
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
