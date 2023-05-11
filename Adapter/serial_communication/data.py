import uuid
from datetime import datetime


class Data:
    def __init__(self, name, value, unit):
        self.id = str(uuid.uuid4())
        self.date_time = datetime.now().strftime("%d-%m-%Y %H.%M.%S")
        self.name = name
        self.value = value
        self.unit = unit

    def to_string(self):
        return "id: " + self.id + \
               ", date_time: " + self.date_time + \
               ", name: " + self.name + \
               ", value: " + self.value + \
               ", unit: " + self.unit
