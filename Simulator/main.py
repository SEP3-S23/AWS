from publisherManager import PublisherManager
from notifyQueue import notify
from config import EXCHANGE
from data import Data
from random import Random
from threading import Thread
import time


class Simulator:

    def __init__(self):
        self.random = Random()
        self.temperature = 20
        self.humidity = 50
        self.wind_speed = 20
        self.pressure = 760
        self.rain_quantity = 0
        self.day = True
        self.publisherManager = PublisherManager(EXCHANGE)

    def get_temperature(self):
        self.temperature += self.random.randrange(-50, 50, 1)/10
        return Data("temperature", self.temperature, "°C")

    def get_humidity(self):
        self.humidity += self.random.randrange(-50, 50, 1)/10
        return Data("humidity", self.humidity, "%")

    def get_feel_like(self):
        value = self.temperature + self.random.randrange(-5, 5, 1)
        return Data("heat index", value, "°C")

    def get_wind_speed(self):
        self.wind_speed += self.random.randrange(-20, 20, 1)
        return Data("wind speed", self.wind_speed, "m/s")

    def get_pressure(self):
        self.pressure += self.random.randrange(-5, 5, 1)
        return Data("pressure", self.pressure, "mm")

    def get_wind_direction(self):
        value = self.random.randrange(0, 360, 1)
        return Data("wind direction", value, "°")

    def get_rain_quantity(self):
        _ = self.random.random()
        if _ > 0.8:
            self.rain_quantity = 0

        if _ < 0.4:
            self.rain_quantity = self.random.randrange(-10, 10, 1)/10 + self.rain_quantity

        return Data("rain quantity", self.rain_quantity, "mm")

    def get_light(self):
        _ = self.random.random()
        if _ > 0.9:
            self.day = not self.day
        if self.day:
            value = "day"
        else:
            value = "night"
        return Data("day", value, "")

    def publish(self):
        self.publisherManager.publish(self.get_temperature(), notify)
        self.publisherManager.publish(self.get_humidity(), notify)
        self.publisherManager.publish(self.get_feel_like(), notify)
        self.publisherManager.publish(self.get_wind_speed(), notify)
        self.publisherManager.publish(self.get_wind_direction(), notify)
        self.publisherManager.publish(self.get_rain_quantity(), notify)
        self.publisherManager.publish(self.get_pressure(), notify)
        self.publisherManager.publish(self.get_light(), notify)


if __name__ == "__main__":
    sim = Simulator()

    while True:
        thread = Thread(target=sim.publish)
        thread.start()
        time.sleep(1)
