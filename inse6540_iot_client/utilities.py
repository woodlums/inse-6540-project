def get_iso_time():
    time_iso = "{:04}-{:02}-{:02} {:02}:{:02}:{:02}".format(*time.gmtime())
    return time_iso