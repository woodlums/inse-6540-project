import network
# import secrets
from utime import sleep, ticks_ms, ticks_diff
import constants

class Wireless:
    
    wlan = None
    
    ip = "0.0.0.0"
    
    def connect(self):
        
        self.wlan = network.WLAN(network.STA_IF)
        self.wlan.active(True)
        self.wlan.connect(constants.WIFI_SSID, constants.WIFI_PASSWORD)
        
        print(constants.WIFI_SSID)
        
        # Wait for connect or fail
        max_wait = 10
        while max_wait > 0:
            if self.wlan.status() < 0 or self.wlan.status() >= 3:
                break
            max_wait -= 1
            print('waiting for connection...')
            sleep(1)
    
        # Handle connection error
        if self.wlan.status() != 3:
           raise RuntimeError('network connection failed')
        else:
          print('Connected')
          status = self.wlan.ifconfig()
          print( 'ip = ' + status[0] )
          self.ip = status[0]
