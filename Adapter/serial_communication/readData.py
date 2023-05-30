import serial
import subprocess
from threading import Thread

from serial import SerialException
import time

class ReadData(Thread):
    def __init__(self, queue):
        self.serial_data = None
        self.serial_port = None

        Thread.__init__(self)
        try:
            self.connect()
        except SerialException:
            print("Connect arduino to the computer")
        finally:
            self.queue = queue
            self.start()

    def connect(self):
        self.serial_port = \
            subprocess.run(["ls /dev/cu.usb*"], capture_output=True, shell=True).stdout.decode('utf-8')[:-1]
        self.serial_data = serial.Serial(self.serial_port, 9600)  # COM port object
        if self.serial_data is None:
            print("Connect arduino to the computer")


    def run(self):
        if self.serial_data is not None:
            print("Reading from the serial port " + self.serial_port)
            while True:
                # collect data from arduino and add to the queue (String)
                if self.serial_data.inWaiting() > 0:
                    self.queue += self.serial_data.readline().decode("utf-8", 'ignore')
        else:
            while self.serial_data is None:
                try:
                    self.connect()
                except SerialException:
                    print("Oh noooo, I'm blind!!\nI can't see any arduino connected :(")
                time.sleep(1)