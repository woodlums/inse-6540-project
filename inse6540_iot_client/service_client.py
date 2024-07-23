import urequests
import ujson

class ServiceClient():

    def do_post(self, url, payload):

        headers = {"Content-Type": "application/json"}
        payload = ujson.dumps(payload)
        
        response = urequests.post(url, headers=headers, data=payload)

        content = response.headers
        print(content)

        print("Response: (" + str(response.status_code) + "), msg = " + str(response.text))

        if response.status_code == 201:
            print("Added Successfully")
        else:
            print("Error")

        response.close()
