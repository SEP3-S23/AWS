import json
from threading import Lock

class Publisher:
    def __init__(self, exchange, name, connection):
        self.lock = Lock()
        self.channel = connection.channel()
        self.EXCHANGE = exchange
        self.name = name

        queue = self.channel.queue_declare(
            queue=self.EXCHANGE + "." + name,
            arguments={'x-message-ttl': 60000}
        )
        self.channel.queue_bind(
            exchange=self.EXCHANGE,
            queue=queue.method.queue,
            routing_key=name
        )

    def __del__(self):
        print('close channel ' + self.name)
        self.channel.close()

    def publish(self, data):
        json_data = json.dumps(data.__dict__, indent=4)
        self.channel.basic_publish(
            exchange=self.EXCHANGE,
            routing_key=data.name,
            body=json_data
        )
        print(json_data)
