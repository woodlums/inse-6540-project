from datetime import timedelta, datetime

import wtforms
from flask import Flask, json, request, jsonify, render_template, session, redirect
from flask_wtf import FlaskForm
from wtforms.fields.datetime import DateField, DateTimeField, TimeField
from wtforms.validators import DataRequired
from wtforms import validators, SubmitField
import mysql.connector
import db
import db_connection_parameters

database = db.DB()

class InfoForm(wtforms.Form):
    startdate = DateField('Start Date', format='%Y-%m-%d', validators=(validators.DataRequired(),))
    enddate = DateField('End Date', format='%Y-%m-%d', validators=(validators.DataRequired(),))
    submit = SubmitField('Submit')

app = Flask(__name__)
app.config['SECRET_KEY'] = '#$%^&*'

@app.route("/")
def hello():
    return render_template("start.html")


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
def get_readings():
    results = database.get_records()
    return jsonify(results)


@app.route('/dashboards/reading', methods=['GET', 'POST'])
def index():
    form = InfoForm()

    if 'startdate' not in request.form and 'enddate' not in request.form:
         form.startdate.data = datetime.now() - timedelta(days=10)
         form.enddate.data = datetime.now()
    else:
        form.startdate.data = datetime.strptime(request.form['startdate'], '%Y-%m-%d')
        form.enddate.data = datetime.strptime(request.form['enddate'], '%Y-%m-%d')

    readings = database.get_records(datetime(form.startdate.data.year, form.startdate.data.month, form.startdate.data.day,0,0,0),
                                    datetime(form.enddate.data.year, form.enddate.data.month, form.enddate.data.day,12,59,59))

    display_columns = database.get_column_names("readings_display")

    return render_template("bootstrap_table.html",
                           title='Readings Management',
                           subtitle='INSE6540 Implementation Project',
                           readings=readings,
                           display_columns=display_columns,
                           form=form,
                           total_readings=len(readings))


if __name__ == "__main__":
    app.run(host='0.0.0.0')
