import urequests
import ujson

class ServiceClient():

    url = "http://172.25.20.2:5000/reading"

    def do_post(self, payload):

        headers = {"Content-Type": "application/json"}
        payload = ujson.dumps(payload)
        
        print(payload)
        
        response = urequests.post(self.url, headers=headers, data=payload)

        resp = response.headers
        print(resp)

        print("Response: (" + str(response.status_code) + "), msg = " + str(response.text))

        if response.status_code == 201:
            print("Added Successfully")
        else:
            print("Error")

        response.close()
