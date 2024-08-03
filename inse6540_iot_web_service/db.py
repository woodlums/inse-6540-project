import mysql.connector
import db_connection_parameters


class DB:

    def __init__(self):
        self.connect()

    def connect(self):
        self.connection = mysql.connector.connect(host=db_connection_parameters.HOST,
                                                  database=db_connection_parameters.DATABASE,
                                                  user=db_connection_parameters.USER,
                                                  password=db_connection_parameters.PASSWORD)

    def query(self, sql, args):
        cursor = self.connection.cursor()
        cursor.execute(sql, args)
        return cursor

    def get_records(self, start_date, end_date):

        mysql_query = f"select * from readings_display where CAST(timestamp as DATE) between '{start_date}' and '{end_date}' order by Timestamp desc;"

        self.connect()

        cursor = self.connection.cursor()
        cursor.execute(mysql_query)

        columns = cursor.description
        records = [{columns[index][0].replace(' ', '_'): column for index, column in enumerate(value)} for value in cursor.fetchall()]

        cursor.close()
        self.connection.close()

        return records

    def get_column_names(self, table_name):
        mysql_query = f"select column_name from INFORMATION_SCHEMA.COLUMNS where table_name = '{table_name}';"

        self.connect()

        cursor = self.connection.cursor()

        cursor.execute(mysql_query)

        columns = cursor.description
        records = [{columns[index][0].replace(' ', '_'): column for index, column in enumerate(value)} for value in
                  cursor.fetchall()]

        cursor.close()
        self.connection.close()

        return [list(x.values())[0] for x in records]

    def get_all_records(self):

        mysql_query = f"select * from readings_display order by timestamp desc;"

        cursor = self.connection.cursor()
        cursor.execute(mysql_query)

        columns = cursor.description
        records = [{columns[index][0].replace(' ', '_'): column for index, column in enumerate(value)} for value in cursor.fetchall()]

        cursor.close()

        return records

    def save_reading(self, reading_value, reading_value_unit, reading_timestamp,
                     reading_type, hashed_value):

        mysql_insert_query = (f"INSERT INTO readings "
                              f"(timestamp, value, unit, reading_type, hashed_value) "
                              f"values "
                              f"('{reading_timestamp}', '{reading_value}', "
                              f"'{reading_value_unit}', '{reading_type}', '{hashed_value}');")

        self.connect();
        cursor = self.connection.cursor()
        cursor.execute(mysql_insert_query)
        self.connection.commit()
        cursor.close()
        self.connection.close()

#    def __del__(self):
#        if self.connection != None:
#            self.connection.close()
