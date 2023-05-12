import pika
from publisher import Publisher
from threading import Lock


class PublisherManager:

    def __init__(self, exchange):
        self.lock = Lock()
        self.EXCHANGE = exchange
        self.publishers = []
        self.connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq'))
        self.channel = self.connection.channel()
        self.channel.exchange_declare(
            exchange=self.EXCHANGE,
            exchange_type='direct'
        )

    def publish(self, data, notify):

        self.lock.acquire()
        publisher = next((x for x in self.publishers if x.name == data.name), None)

        if publisher is None:
            publisher = Publisher(self.EXCHANGE, data.name, self.connection)
            self.publishers.append(publisher)
            self.lock.release()
            notify(data.name, self.EXCHANGE)
        else:
            self.lock.release()
            publisher.publish(data)

