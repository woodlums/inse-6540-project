import hashlib
import internal_storage as ist
import service_client
import os
import utilities


def save_value(rtype, value, unit):
    
    sc = service_client.ServiceClient()

    time_iso = utilities.get_iso_time()

    # Build the hashed value including salt.
    salt = os.urandom(16)
    text = f"{time_iso}{rtype}{value}{unit}".encode("utf-8")
    hashed_value = hashlib.sha256(salt + text).digest().hex() + ':' + salt.hex()

    try:
        # Save to internal storage
        ist.write_csv(time_iso, value, unit, rtype, hashed_value)
        
        # Save to service
        post_data = {'reading_value': value, 'reading_value_unit': unit,
                     'reading_timestamp': time_iso, 'reading_type': rtype, 'hashed_value': hashed_value}
        sc.do_post(post_data)
    
    except Exception as e:
        # Report inconsistency
        ist.write_exception_record(hashed_value)
        print("Error with record:" + hashed_value)
        print(e)
