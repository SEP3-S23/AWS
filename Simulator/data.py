import uuid
from datetime import datetime
from config import EXCHANGE
import pytz

class Data:
    def __init__(self, name, value, unit):
        self.id = str(uuid.uuid4())
        self.date_time = datetime.now(pytz.timezone('Europe/Rome')).timestamp()
        self.wsName = EXCHANGE
        self.name = name
        self.value = value
        self.unit = unit

    def to_string(self):
        return "id: " + self.id + \
            ", date_time: " + self.date_time + \
            ", exchange: " + self.wsName + \
            ", name: " + self.name + \
            ", value: " + self.value + \
            ", unit: " + self.unit
