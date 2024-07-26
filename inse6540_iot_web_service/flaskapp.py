from flask import Flask, json, request, jsonify
import mysql.connector
import db_connection_parameters

app = Flask(__name__)


@app.route("/")
def hello():
    return "<b>INSE6540 Project</b>"


@app.route("/reading", methods=['POST'])
def create_reading():
    # reading_value = request.form.get('reading_value')
    # reading_value_unit = request.form.get('reading_value_unit')
    # reading_timestamp = request.form.get('reading_timestamp')
    # reading_type = request.form.get('reading_type')

    global connection
    incoming = request.json
    reading_value = incoming.get('reading_value')
    reading_value_unit = incoming.get('reading_value_unit')
    reading_timestamp = incoming.get('reading_timestamp')
    reading_type = incoming.get('reading_type')

    try:
        connection = mysql.connector.connect(host=db_connection_parameters.HOST,
                                             database=db_connection_parameters.DATABASE,
                                             user=db_connection_parameters.USER,
                                             password=db_connection_parameters.PASSWORD)

        mysql_insert_query = (f"INSERT INTO readings "
                              f"(date_time, value, unit, reading_type) "
                              f"values "
                              f"('{reading_timestamp}', '{reading_value}', "
                              f"'{reading_value_unit}', '{reading_type}');")
        print(mysql_insert_query)

        cursor = connection.cursor()
        cursor.execute(mysql_insert_query)
        connection.commit()

    except mysql.connector.Error as error:
        print("Failed to insert record {}".format(error))
        return json.dumps({'success': False}), 500, {'ContentType': 'application/json'}

    finally:
        if connection.is_connected():
            connection.close()
            print("MySQL connection closed.")

    connection.close()

    return json.dumps({'success': True}), 201, {'ContentType': 'application/json'}


@app.route("/reading", methods=['GET'])
def testdb():
    connection = mysql.connector.connect(host='172.25.20.3',
                                         database='inse6540',
                                         user='chicken',
                                         password='bokbokbok')

    mysql_insert_query = (f"select * from readings;")

    cursor = connection.cursor()
    cursor.execute(mysql_insert_query)

    results = cursor.fetchall()
    print(results)

    cursor.close()

    return jsonify(results)


if __name__ == "__main__":
    app.run(host='0.0.0.0')
