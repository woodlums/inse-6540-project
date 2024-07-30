import mysql.connector
import db_connection_parameters


class DB:

    def __init__(self):
        self.connection = mysql.connector.connect(host=db_connection_parameters.HOST,
                                                  database=db_connection_parameters.DATABASE,
                                                  user=db_connection_parameters.USER,
                                                  password=db_connection_parameters.PASSWORD)

        self.cursor = self.connection.cursor()

    def get_records(self, limit):
        if limit == None:
            mysql_insert_query = f"select * from readings;"
        else:
            mysql_insert_query = f"select * from readings limit {limit};"

        self.cursor.execute(mysql_insert_query)
        records = self.cursor.fetchall()
        return records

    def save_reading(self, reading_value, reading_value_unit, reading_timestamp,
                     reading_type, hashed_value):
        mysql_insert_query = (f"INSERT INTO readings "
                              f"(date_time, value, unit, reading_type, hashed_value) "
                              f"values "
                              f"('{reading_timestamp}', '{reading_value}', "
                              f"'{reading_value_unit}', '{reading_type}', '{hashed_value}');")

        self.cursor.execute(mysql_insert_query)
        self.connection.commit()
