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
	"encoding/json"
	"github.com/NyCodeGHG/docky/connection"
	"github.com/gofiber/fiber/v2"
	"github.com/joho/godotenv"
	"go.uber.org/zap"
)

func main() {
	err := godotenv.Load()
	sugar := zap.NewExample().Sugar()
	defer sugar.Sync()
	app := fiber.New()

	con, err := connection.CreateDockerConnection()

	if err != nil {
		sugar.Fatal("Unable to establish a connection to the Docker Engine!", err)
	}

	version, err := con.GetVersion()

	if err != nil {
		sugar.Fatal("Unable to fetch Docker Engine Version from Connection", err)
	}

	sugar.Infof("Connected to Docker Socket with Version: %s", version.Version)

	app.Get("/", func(c *fiber.Ctx) error {
		message := map[string]string{"name": "Docky", "author": "NyCode", "url": "https://github.com/NyCodeGHG/docky", "license": "Apache-2.0"}
		messageJson, _ := json.Marshal(message)
		return c.Send(messageJson)
	})

	app.Get("/containers/", func(c *fiber.Ctx) error {
		containers, err := con.GetAllContainers()
		if err != nil {
			return err
		}

		containerJson, err := json.Marshal(containers)

		if err != nil {
			return err
		}

		return c.Send(containerJson)
	})

	app.Post("/restart/:container", func(c *fiber.Ctx) error {
		containerId := c.Params("container")
		container, err := con.GetContainer(containerId)
		if err != nil {
			return err
		}

		go func() {
			err := con.Restart(container)
			if err != nil {
				sugar.Errorf("Received error when restarting container %s", containerId)
				sugar.Error(err)
			}
		}()
		return c.SendStatus(200)
	})

	app.Post("/redeploy/:container", func(c *fiber.Ctx) error {
		containerId := c.Params("container")
		container, err := con.GetContainer(containerId)
		if err != nil {
			return err
		}

		go func() {
			err := con.Redeploy(container)
			if err != nil {
				sugar.Errorf("Received error when redeploying container %s", containerId)
				sugar.Error(err)
			}
		}()
		return c.SendStatus(200)
	})

	_ = app.Listen(":3000")
	err = con.Close()
	if err != nil {
		panic(err)
	}
}
