import time
from machine import Pin
from dht11 import DHT11, InvalidChecksum
import utime
import wireless_control
import service_client
import sys
import constants

w = wireless_control.Wireless()
w.connect()

request_url = "http://172.25.20.2:5000/reading"

sc = service_client.ServiceClient()

pin = Pin(28, Pin.OUT, Pin.PULL_DOWN)
sensor = DHT11(pin)

while True:

    t = sensor.temperature
    
    print("Temperature: {}".format(t))
        
    time_iso = "{:04}-{:02}-{:02} {:02}:{:02}:{:02}".format(*time.gmtime())
    reading = {'reading_value': t,'reading_value_unit':'C','reading_timestamp':time_iso,'reading_type':'T'}
    
    sc.do_post(request_url, reading)
    
    h = sensor.humidity
    
    print("Humidity: {}".format(h))
    
    time_iso = "{:04}-{:02}-{:02} {:02}:{:02}:{:02}".format(*time.gmtime())
    reading = {'reading_value': h,'reading_value_unit':'R','reading_timestamp':time_iso,'reading_type':'H'}
    
    sc.do_post(request_url, reading)
    
    # with open("readings.dat", 'a') as file1:
    #    file1.write(f"Temp {sensor.temperature}\n")
    #    file1.write(f"Humid {sensor.humidity}\n")

    time.sleep(constants.READING_INTERVAL * 60)
    