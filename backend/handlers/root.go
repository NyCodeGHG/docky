package handlers

import (
	"encoding/json"
	"net/http"
)

func RootHandler(w http.ResponseWriter, _ *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	message := map[string]string{"name": "Docky", "author": "NyCode", "url": "https://github.com/NyCodeGHG/docky"}
	messageJson, _ := json.Marshal(message)

	_, err := w.Write(messageJson)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
