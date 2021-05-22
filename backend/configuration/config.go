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
