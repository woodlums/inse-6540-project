create or replace table inse6540.reading_type
(
    type_code char(2)     not null
        primary key,
    type_name varchar(45) null
);

create or replace table inse6540.severity
(
    id          int         not null
        primary key,
    explanation varchar(45) null
);

create or replace table inse6540.logs
(
    id       int auto_increment
        primary key,
    date     datetime default current_timestamp() not null,
    log_text varchar(45)                          null,
    severity int                                  null,
    constraint fk_severity
        foreign key (severity) references inse6540.severity (id)
);

create or replace index fk_severity_idx
    on inse6540.logs (severity);

create or replace table inse6540.units
(
    unit_code char        not null
        primary key,
    unit_name varchar(45) null
);

create or replace table inse6540.readings
(
    id           int auto_increment
        primary key,
    date_time    datetime default current_timestamp() not null,
    value        float                                not null,
    unit         char                                 not null,
    reading_type char(2)                              not null,
    constraint fk_type_code
        foreign key (reading_type) references inse6540.reading_type (type_code),
    constraint fk_unit
        foreign key (unit) references inse6540.units (unit_code)
);

create or replace index fk_reading_type_idx
    on inse6540.readings (reading_type);

create or replace index fk_unit_idx
    on inse6540.readings (unit);

