from threading import Thread
from .data import Data
from pubsub import pub

class GenerateMessage(Thread):
    def __init__(self, queue):
        Thread.__init__(self)
        self.queue = queue
        self.message = ""
        self.start()

    def run(self):
        print("Start receiving message form Arduino")
        while True:
            # get the first char from the string and add to the message
            if len(self.queue) > 0:
                # \n means the end of the message
                if self.queue[0] == "\n":
                    self.queue.pop(0)
                    data = self.message.replace("\r", "").split(":")

                    print("New message arrived from Arduino")
                    self.publish(data)

                    # server.send(bytes(json_obj, encoding="utf-8"))
                    self.message = ""
                else:
                    self.message += self.queue.pop(0)

    @staticmethod
    def publish(data):
        pub.sendMessage('message', data=Data(data[0], data[1], data[2]))

