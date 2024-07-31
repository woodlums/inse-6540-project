import time, utime
from machine import Pin
from dht11 import DHT11, InvalidChecksum
import wireless_control
import service_client
import sys
import constants
import bl
import internal_storage as ist
from oled import OLED

ol = OLED()
ol.add_line("-- START --")

w = wireless_control.Wireless()
w.connect()

ol.add_line(f"IP:{w.ip}")
ol.redraw()

pin = Pin(28, Pin.OUT, Pin.PULL_DOWN)
sensor = DHT11(pin)

ist.create_data_header()
ist.create_log_header()

while True:

    try:
        # The sensor is slow, give it some time.
        utime.sleep_ms(500)
        
        t = sensor.temperature # Temp
        ol.add_line(f"Temp:{t}")
        utime.sleep_ms(50)
        ol.redraw()
        bl.save_value("T", t, "C")
        
        # The sensor is slow, give it some time.
        utime.sleep_ms(500)
        
        h = sensor.humidity # Humid
        ol.add_line(f"Hum:{h}")
        utime.sleep_ms(50)
        ol.redraw()
        bl.save_value("H", h, "R")

    except Exception as e:
        
        print("Exception occurred in main")
        print(e)
        ist.write_log_record("Exception in " + __name__)
        ist.write_log_record(e)

    # Time between reading groups.
    time.sleep(constants.READING_INTERVAL * 60)
