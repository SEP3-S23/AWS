from serial_communication.serialCommunication import SerialCommunication
from publisherManager import PublisherManager
from pubsub import pub
from notifyQueue import notify
from config import EXCHANGE
from threading import Thread

publisherManager = PublisherManager(EXCHANGE)


def start_app():
    pub.subscribe(publish, 'message')
    SerialCommunication().start()


def publish(data):
    #thread = Thread(target=publisherManager.publish, args=(data, notify))
    #thread.start()

    publisherManager.publish(data, notify)


if __name__ == "__main__":
    start_app()
