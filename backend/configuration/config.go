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

package configuration

import (
	"github.com/spf13/viper"
	"log"
)

type Config struct {
	Port int
	Host string
}

var viperInitialized = false

func initializeViper() {
	if viperInitialized {
		return
	}
	log.Println("Reading configuration...")

	viper.SetConfigName("config")
	viper.SetConfigType("toml")

	viper.SetDefault("Port", 8080)
	viper.SetDefault("Host", "0.0.0.0")
	viper.SetDefault("Username", "")
	viper.SetDefault("Password", "")
	viper.SetDefault("Registry", "")
	viper.AddConfigPath("/etc/docky/")
	viper.AddConfigPath("$HOME/.docky")
	viper.AddConfigPath(".")

	err := viper.ReadInConfig()
	if err != nil {
		log.Println("No config file was found!")
	}
	log.Println("Reading environment variables!")
	viper.AutomaticEnv()
	viperInitialized = true
}

// ReadConfig Reads the configuration from viper
func ReadConfig() (config Config) {
	initializeViper()

	config = Config{
		viper.GetInt("Port"),
		viper.GetString("Host"),
	}
	return
}
