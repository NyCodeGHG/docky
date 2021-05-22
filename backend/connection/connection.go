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
	"github.com/docker/docker/api/types"
	"github.com/docker/docker/client"
	"log"
	"time"
)

var cli *client.Client

func init() {
	cli, _ = client.NewClientWithOpts(client.FromEnv, client.WithAPIVersionNegotiation())
}

func GetAllContainers() ([]types.Container, error) {
	ctx := context.Background()
	containers, err := cli.ContainerList(ctx, types.ContainerListOptions{})
	if err != nil {
		return nil, err
	}
	return containers, nil
}

func GetContainer(name string) (target *types.Container, err error) {
	containers, err := GetAllContainers()
	if err != nil {
		return
	}

	for _, container := range containers {
		if container.ID == name {
			target = &container
		}
	}
	return
}

func Restart(container *types.Container) error {
	ctx := context.Background()
	log.Printf("Redeploying %s\n", container.ID)

	log.Println("Restarting container...")
	duration := 5 * time.Second
	err := cli.ContainerRestart(ctx, container.ID, &duration)
	if err != nil {
		return err
	}
	return nil
}

func Redeploy(container *types.Container) error {
	ctx := context.Background()
	log.Printf("Redeploying %s\n", container.ID)

	log.Println("Stopping container...")
	duration := 5 * time.Second
	err := cli.ContainerStop(ctx, container.ID, &duration)
	if err != nil {
		return err
	}

	log.Println("Removing image...")
	_, err = cli.ImageRemove(ctx, container.ImageID, types.ImageRemoveOptions{
		Force: true,
	})
	if err != nil {
		return err
	}

	log.Println("Pulling image...")
	_, err = cli.ImagePull(ctx, container.Image, types.ImagePullOptions{})
	if err != nil {
		return err
	}

	log.Println("Starting container...")
	err = cli.ContainerStart(ctx, container.ID, types.ContainerStartOptions{})
	if err != nil {
		return err
	}
	return nil
}
