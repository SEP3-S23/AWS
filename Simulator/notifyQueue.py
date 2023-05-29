import requests
from config import NOTIFY_URL

def notify(queue, exchange):

    try:
        x = requests.post(NOTIFY_URL, json={'name': exchange, "sensor": queue})
        print('notified -> ' + exchange + '.' + queue)
    except requests.exceptions.RequestException as e:
        print(e)