from flask import Flask, json, request, jsonify
import mysql.connector

import db
import db_connection_parameters

database = db.DB()

app = Flask(__name__)


@app.route("/")
def hello():
    return "<b>INSE6540 Project</b>"


@app.route("/reading", methods=['POST'])
def create_reading():
    incoming = request.json
    reading_value = incoming.get('reading_value')
    reading_value_unit = incoming.get('reading_value_unit')
    reading_timestamp = incoming.get('reading_timestamp')
    reading_type = incoming.get('reading_type')
    hashed_value = incoming.get('hashed_value')

    try:
        connection = mysql.connector.connect(host=db_connection_parameters.HOST,
                                             database=db_connection_parameters.DATABASE,
                                             user=db_connection_parameters.USER,
                                             password=db_connection_parameters.PASSWORD)

        database.save_reading(reading_value, reading_value_unit, reading_timestamp,
                              reading_type, hashed_value)

    except mysql.connector.Error as error:

        return json.dumps({'success': False}), 500, {'ContentType': 'application/json'}

    finally:
        if connection.is_connected():
            connection.close()
            print("MySQL connection closed.")

    connection.close()

    return json.dumps({'success': True}), 201, {'ContentType': 'application/json'}


@app.route("/reading", methods=['GET'])
def testdb():
    limit = request.args.get('limit', None)
    results = database.get_records(limit)
    return jsonify(results)


if __name__ == "__main__":
    app.run(host='0.0.0.0')
