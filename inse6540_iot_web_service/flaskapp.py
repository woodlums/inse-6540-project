from datetime import timedelta, datetime

from flask import Flask, json, request, jsonify, render_template, session, redirect
from flask_wtf import FlaskForm
from wtforms.fields.datetime import DateField
from wtforms.validators import DataRequired
from wtforms import validators, SubmitField
import mysql.connector
import db
import db_connection_parameters

database = db.DB()

class InfoForm(FlaskForm):
    startdate = DateField('Start Date', format='%Y-%m-%d', validators=(validators.DataRequired(),))
    enddate = DateField('End Date', format='%Y-%m-%d', validators=(validators.DataRequired(),))
    submit = SubmitField('Submit')

app = Flask(__name__)
app.config['SECRET_KEY'] = '#$%^&*'

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


@app.route('/dashboards/reading', methods=['GET', 'POST'])
def index():
    form = InfoForm()

    if 'startdate' not in session and 'enddate' not in session:
         form.startdate.data = datetime.now() - timedelta(days=1)
         form.enddate.data = datetime.now()


    readings = database.get_records(form.startdate.data, form.enddate.data)

    display_columns = [x for x in readings[0].keys()]


    if form.validate_on_submit():
        session['startdate'] = form.startdate.data
        session['enddate'] = form.enddate.data
        #return redirect('date')

    return render_template('bootstrap_table.html', title='Blockchain Enabled IOT Readings',
                           readings=readings,
                           display_columns=display_columns,
                           form=form)


@app.route('/dashboards/date', methods=['GET','POST'])
def date():
    startdate = session['startdate']
    enddate = session['enddate']
    return render_template('date.html')


if __name__ == "__main__":
    app.run(host='0.0.0.0')
