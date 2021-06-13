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

package connection

import (
	"context"
	"encoding/base64"
	"errors"
	"fmt"
	"github.com/docker/docker/api/types"
	"github.com/docker/docker/client"
	"log"
	"os"
	"time"
)

type DockerConnection struct {
	client *client.Client
}

func CreateDockerConnection() (*DockerConnection, error) {
	cli, err := client.NewClientWithOpts(client.FromEnv, client.WithAPIVersionNegotiation())
	return &DockerConnection{cli}, err
}

func (connection *DockerConnection) GetVersion() (*types.Version, error) {
	ctx := context.Background()
	version, err := connection.client.ServerVersion(ctx)
	return &version, err
}

func (connection *DockerConnection) GetAllContainers() ([]types.Container, error) {
	ctx := context.Background()
	containers, err := connection.client.ContainerList(ctx, types.ContainerListOptions{})
	if err != nil {
		return nil, err
	}
	return containers, nil
}

func (connection *DockerConnection) GetContainer(id string) (target *types.Container, err error) {
	containers, err := connection.GetAllContainers()
	if err != nil {
		return
	}

	for _, container := range containers {
		if container.ID == id {
			target = &container
			break
		}
	}

	if target == nil {
		err = errors.New("container not found")
	}

	return
}

func (connection *DockerConnection) Restart(container *types.Container) error {
	ctx := context.Background()
	log.Printf("Restarting  %s\n", container.ID)

	log.Println("Restarting container...")
	duration := 5 * time.Second
	err := connection.client.ContainerRestart(ctx, container.ID, &duration)
	if err != nil {
		return err
	}
	return nil
}

func (connection *DockerConnection) Redeploy(container *types.Container) error {
	ctx := context.Background()
	log.Printf("Redeploying %s\n", container.ID)

	log.Println("Stopping container...")
	duration := 5 * time.Second
	err := connection.client.ContainerStop(ctx, container.ID, &duration)
	if err != nil {
		return err
	}

	log.Println("Removing image...")
	_, err = connection.client.ImageRemove(ctx, container.ImageID, types.ImageRemoveOptions{
		Force: true,
	})
	if err != nil {
		return err
	}

	username := os.Getenv("USERNAME")
	password := os.Getenv("PASSWORD")

	log.Println("Pulling image...")
	_, err = connection.client.ImagePull(ctx, container.Image, types.ImagePullOptions{
		RegistryAuth: base64.StdEncoding.EncodeToString([]byte(fmt.Sprintf("{\"username\": \"%s\", \"password\": \"%s\"}", username, password))),
	})
	if err != nil {
		return err
	}

	log.Println("Starting container...")
	err = connection.client.ContainerStart(ctx, container.ID, types.ContainerStartOptions{})
	if err != nil {
		return err
	}
	return nil
}

func (connection *DockerConnection) Close() error {
	return connection.client.Close()
}
