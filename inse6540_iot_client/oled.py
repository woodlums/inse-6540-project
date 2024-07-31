from machine import Pin, I2C
from ssd1306 import SSD1306_I2C
import utime

WIDTH =128 
HEIGHT= 64

i2c=I2C(0,scl=Pin(1),sda=Pin(0),freq=200000)

class OLED:
    def __init__(self):
        self.text_list = ["", "", "", ""]
        
        self.oled = SSD1306_I2C(WIDTH,HEIGHT,i2c)

    def add_line(self, text):
        self.text_list.pop()
        self.text_list = [text] + self.text_list
        
    def add_line_datestamped(self, text):
        self.text_list.pop()
        text = str(utime.gmtime()) + " " + text
        self.text_list = [text] + self.text_list
        
    def redraw(self):
        self.oled.fill(0)
        self.oled.text(self.text_list[3], 0, 0)
        self.oled.text(self.text_list[2], 0, 15)
        self.oled.text(self.text_list[1], 0, 30)
        self.oled.text(self.text_list[0], 0, 45)
        self.oled.show()
