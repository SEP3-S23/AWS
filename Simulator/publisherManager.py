import pika
from publisher import Publisher
from config import RABBITMQ_HOST

class PublisherManager:

    def __init__(self, exchange):
        self.EXCHANGE = exchange
        self.publishers = []
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(RABBITMQ_HOST))
        self.channel = self.connection.channel()
        self.channel.exchange_declare(
            exchange=self.EXCHANGE,
            exchange_type='direct'
        )

    def publish(self, data, notify):

        publisher = next((x for x in self.publishers if x.name == data.name), None)

        if publisher is None:
            publisher = Publisher(self.EXCHANGE, data.name, self.connection)
            self.publishers.append(publisher)
            notify(data.name, self.EXCHANGE)
        else:
            publisher.publish(data)

