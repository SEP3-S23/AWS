from .readData import ReadData
from .generateMessage import GenerateMessage


class SerialCommunication:
    def __init__(self):
        self.queue = []

    def start(self):
        ReadData(self.queue)
        GenerateMessage(self.queue)
