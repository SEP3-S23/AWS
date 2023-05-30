from publisherManager import PublisherManager
from notifyQueue import notify
from config import EXCHANGE
from data import Data
from random import Random
from threading import Thread
import time
import math


class Simulator:

    def __init__(self):
        self.random = Random()
        self.temperature = 20
        self.humidity = 50
        self.wind_speed = 20
        self.pressure = 760
        self.rain_quantity = 0
        self.weather_condition = ""
        self.publisherManager = PublisherManager(EXCHANGE)

        self.weatherConditionStatus = [
            "thunderstorms-day-rain",
            "clear-day",
            "partly-cloudy-day-rain",
            "partly-cloudy-day-snow",
            "partly-cloudy-day-fog",
            "partly-cloudy-day-hail"
        ]

        self.weather_condition_counter = 10

    def get_temperature(self):
        return Data("temperature", self.temperature + self.random.randrange(-10, 10, 1)/10, "°C")

    def get_humidity(self):
        return Data("humidity", self.humidity + self.random.randrange(-10, 10, 1)/10, "%")

    def get_UV_index(self):
        value = self.random.randrange(1, 11, 1)
        return Data("UV index", value, "")

    def get_wind_speed(self):
        return Data("wind speed", self.wind_speed + self.random.randrange(-20, 20, 1)/10, "m/s")

    def get_pressure(self):
        return Data("pressure", self.pressure + self.random.randrange(-5, 5, 1), "mm")

    def get_wind_direction(self):
        value = self.random.randrange(0, 360, 1)
        return Data("wind direction", value, "°")

    def get_rain_quantity(self):
        _ = self.random.random()
        if _ > 0.8:
            self.rain_quantity = 0

        if _ < 0.4:
            self.rain_quantity = self.random.randrange(-10, 10, 1)/10

        return Data("rain quantity", self.rain_quantity, "mm")

    def get_weather_condition(self):
        if self.weather_condition_counter == 0:
            _ = self.random.random()
            _ *= len(self.weatherConditionStatus)
            _ = math.floor(_)
            self.weather_condition = self.weatherConditionStatus[_]
            self.weather_condition_counter = 10

        self.weather_condition_counter -= 1
        return Data("weather condition", self.weather_condition, "")

    def publish(self):
        self.publisherManager.publish(self.get_temperature(), notify)
        self.publisherManager.publish(self.get_humidity(), notify)
        self.publisherManager.publish(self.get_UV_index(), notify)
        self.publisherManager.publish(self.get_wind_speed(), notify)
        self.publisherManager.publish(self.get_wind_direction(), notify)
        self.publisherManager.publish(self.get_rain_quantity(), notify)
        self.publisherManager.publish(self.get_pressure(), notify)
        self.publisherManager.publish(self.get_weather_condition(), notify)


if __name__ == "__main__":
    sim = Simulator()

    while True:
        thread = Thread(target=sim.publish)
        thread.start()
        time.sleep(1)
