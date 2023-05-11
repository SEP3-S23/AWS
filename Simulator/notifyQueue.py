import requests


def notify(queue, exchange):
    url = 'http://localhost:8080/api/queue'
    payload = {
        'name': exchange + "." + queue
    }

    try:
        x = requests.post(url, json=payload)
        print(x.status_code)
    except requests.exceptions.RequestException as e:
        print(e)
    print("notified the DataServer about " + payload['name'] + " queue")
