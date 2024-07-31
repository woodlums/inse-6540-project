import mysql.connector
import db_connection_parameters


class DB:

    def __init__(self):
        self.connection = mysql.connector.connect(host=db_connection_parameters.HOST,
                                                  database=db_connection_parameters.DATABASE,
                                                  user=db_connection_parameters.USER,
                                                  password=db_connection_parameters.PASSWORD)

        self.cursor = self.connection.cursor()

    def get_records(self, start_date, end_date):

        mysql_query = f"select * from readings_display where CAST(timestamp as DATE) between '{start_date}' and '{end_date}' order by Timestamp desc;"

        self.cursor.execute(mysql_query)

        columns = self.cursor.description
        records = [{columns[index][0].replace(' ', '_'): column for index, column in enumerate(value)} for value in self.cursor.fetchall()]

        return records

    def get_column_names(self, table_name):
        mysql_query = f"select column_name from INFORMATION_SCHEMA.COLUMNS where table_name = '{table_name}';"

        self.cursor.execute(mysql_query)

        columns = self.cursor.description
        records = [{columns[index][0].replace(' ', '_'): column for index, column in enumerate(value)} for value in
                   self.cursor.fetchall()]

        return [list(x.values())[0] for x in records]

    def get_all_records(self):

        mysql_query = f"select * from readings_display order by timestamp desc;"

        self.cursor.execute(mysql_query)

        columns = self.cursor.description
        records = [{columns[index][0].replace(' ', '_'): column for index, column in enumerate(value)} for value in self.cursor.fetchall()]

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
