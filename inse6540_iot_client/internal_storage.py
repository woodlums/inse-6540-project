import os
import time

def write_csv(timestamp, value, unit, reading_type, hashed_value):
    
    csv_record = f"\"{timestamp}\",{value},\"{unit}\",\"{reading_type}\",{hashed_value}\n"

    with open("readings.dat", 'a') as file1:
        file1.write(csv_record)

def write_exception_record(msg):
    
    time_iso = "{:04}-{:02}-{:02} {:02}:{:02}:{:02}".format(*time.gmtime())
    record = f"\"{time_iso}\",{msg},\"\n"

    with open("log.dat", 'a') as file1:
        file1.write(record)

def write_log_record(msg):
    
    time_iso = "{:04}-{:02}-{:02} {:02}:{:02}:{:02}".format(*time.gmtime())
    record = f"\"{time_iso}\",\"{msg}\"\n"

    with open("log.dat", 'a') as file1:
        file1.write(record)

def create_data_header():
  
    with open("readings.dat", 'a') as file1:
        content_size = os.stat("readings.dat")[6]
        if content_size == 0:
            file1.write(f"\"timestamp\",\"value\",\"unit\",\"reading_type\",\"hashed_value\"\n")
            print("Created readings CSV Header")

def create_log_header():
    
    with open("log.dat", 'a') as file1:
        content_size = os.stat("log.dat")[6]
        if content_size == 0:
            file1.write(f"\"timestamp\",\"message\"\n")
            print("Created log CSV Header")
